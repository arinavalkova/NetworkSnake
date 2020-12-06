package game.multi.receive;

import dto.GameMessage;
import game.multi.GamePlay;
import game.multi.Network;
import game.multi.receive.handlers.*;
import game.multi.sender.milticast.ByteMessage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReceiverFactory {
    private Network network;
    private GamePlay gamePlay;

    private final CurrentGames currentGames;
    private final List<GameMessage> waitingForProcessingMessages;

    private boolean isReceiverFromUnicastWork;
    private boolean isReceiverFromMulticastWork;

    private final Thread receiverFromUnicast = new Thread(() -> {
        while (isReceiverFromUnicastWork) {
            try {
                ByteMessage receivedMessage = network.receiveFromSocket();
                GameMessage gameMessage = GameMessage.parseFrom(receivedMessage.getMessage());
                MessageHandler messageHandler = messageHandlerMap.get(gameMessage.getTypeCase().getNumber());
                messageHandler.handle(receivedMessage.getSocketAddress(), this, gameMessage);
            } catch (IOException e) {
                break;
            }
        }
    });

    private final Thread receiverFromMulticast = new Thread(() -> {
        while (isReceiverFromMulticastWork) {
            try {
                ByteMessage receivedMessage = network.receiveFromMulticast();
                GameMessage gameMessage = GameMessage.parseFrom(receivedMessage.getMessage());
                MessageHandler messageHandler = messageHandlerMap.get(gameMessage.getTypeCase().getNumber());
                messageHandler.handle(receivedMessage.getSocketAddress(), this, gameMessage);
            } catch (IOException e) {
                break;
            }
        }
    });

    public ReceiverFactory(Network network, GamePlay gamePlay) {
        this.network = network;
        this.gamePlay = gamePlay;
        this.waitingForProcessingMessages = new CopyOnWriteArrayList<>();
        this.currentGames = new CurrentGames();
        this.isReceiverFromUnicastWork = true;
        this.isReceiverFromMulticastWork = true;
    }

    public void start() {
        receiverFromMulticast.start();
    }

    public void stop() {
        //если я мастер отправить deputy очередь с необработанными сообщениями
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

    public void setGamePlay(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }
}
