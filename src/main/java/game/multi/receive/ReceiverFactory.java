package game.multi.receive;

import com.google.protobuf.InvalidProtocolBufferException;
import dto.GameMessage;
import game.multi.GamePlay;
import game.multi.Network;
import game.multi.receive.handlers.*;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReceiverFactory {
    private Network network;
    private GamePlay gamePlay;

    private final CurrentGames currentGames;
    private Map<SocketAddress, GameMessage> messagesBuffer;
    private final List<GameMessage> waitingForProcessingMessages;

    private boolean isProcessorWork;
    private boolean isReceiverFromUnicastWork;
    private boolean isReceiverFromMulticastWork;

    private final Thread receiverFromUnicast = new Thread(() -> {
        while (isReceiverFromUnicastWork) {
            try {
                messagesBuffer.put(
                        null,
                        GameMessage.parseFrom(
                                network.receiveFromSocket()
                        )
                );
            } catch (IOException e) {
                break;
            }
        }
    });

    private final Thread receiverFromMulticast = new Thread(() -> {
        while (isReceiverFromMulticastWork) {
            Iterator<Map.Entry<SocketAddress, byte[]>> receivedMessage =
                    network.receiveFromMulticast().entrySet().iterator();
            if (!receivedMessage.hasNext()) {
                continue;
            }
            try {
                Map.Entry<SocketAddress, byte[]> messageEntry = receivedMessage.next();
                System.out.println(messageEntry.getValue().length);
                messagesBuffer.put(
                        messageEntry.getKey(),
                        GameMessage.parseFrom(
                                messageEntry.getValue()
                        )
                );
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }
    });

    private final Thread processor = new Thread(() -> {
        while (isProcessorWork) {
            if (messagesBuffer.isEmpty()) {
                continue;
            }
            for (Map.Entry<SocketAddress, GameMessage> messageEntry : messagesBuffer.entrySet()) {
                MessageHandler messageHandler = messageHandlerMap.get(messageEntry.getValue()
                        .getTypeCase().getNumber());
                messageHandler.handle(messageEntry.getKey(), this, messageEntry.getValue());
            }
        }
    });

    public ReceiverFactory(Network network, GamePlay gamePlay) {
        this.network = network;
        this.gamePlay = gamePlay;
        this.messagesBuffer = new ConcurrentHashMap<>();
        this.waitingForProcessingMessages = new CopyOnWriteArrayList<>();
        this.currentGames = new CurrentGames();
        this.isProcessorWork = true;
        this.isReceiverFromUnicastWork = true;
        this.isReceiverFromMulticastWork = true;
    }

    public void start() {
        receiverFromUnicast.start();
        receiverFromMulticast.start();
        processor.start();
    }

    public void stop() {
        //если я мастер отправить deputy очередь с необработанными сообщениями
        this.isProcessorWork = false;
        this.isReceiverFromUnicastWork = false;
        this.isReceiverFromMulticastWork = false;
    }

    private static final Map<Integer, MessageHandler> messageHandlerMap = Map.of(
            2, new PingMessageHandler()
            , 3, new SteerMessageHandler()
            , 4, new AckMessageHandler()
            , 5, new StateMessageHandler()
            , 6, new AnnouncmentMessageHandler()
            , 7, new JoinMessageHandler()
            , 8, new ErrorMessageHandler()
            , 9, new RoleChangeMessageHandler()
    );

    public GamePlay getGame() {
        return gamePlay;
    }

    public CurrentGames getCurrentGames() {
        return currentGames;
    }

    public List<GameMessage> getWaitingForProcessingMessages() {
        return waitingForProcessingMessages;
    }
}
