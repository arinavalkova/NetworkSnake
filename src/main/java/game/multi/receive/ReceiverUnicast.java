package game.multi.receive;

import dto.GameMessage;
import game.multi.GamePlay;
import game.multi.Network;
import game.multi.receive.handlers.*;
import game.multi.sender.milticast.ByteMessage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReceiverUnicast {
    private GamePlay gamePlay;
    private boolean isReceiverFromUnicastWork;
    private Network network;
    private final List<GameMessage> steerMsgStorage;

    private final Thread receiverFromUnicast = new Thread(() -> {
        while (isReceiverFromUnicastWork) {
            try {
                ByteMessage receivedMessage = network.receiveFromSocket();
                GameMessage gameMessage = GameMessage.parseFrom(receivedMessage.getMessage());
                MessageHandler messageHandler = messageHandlerMap.get(gameMessage.getTypeCase().getNumber());
                messageHandler.handle(receivedMessage.getSocketAddress(), gamePlay, gameMessage);
            } catch (IOException e) {
                break;
            }
        }
    });

    public ReceiverUnicast(Network network, GamePlay gamePlay) {
        this.gamePlay = gamePlay;
        this.isReceiverFromUnicastWork = true;
        this.network = network;
        this.steerMsgStorage = new CopyOnWriteArrayList<>();
    }

    public void start() {
        receiverFromUnicast.start();
    }

    public void stop() {
        this.isReceiverFromUnicastWork = false;
    }

    private static final Map<Integer, MessageHandler> messageHandlerMap = Map.of(
            3, new SteerMessageHandler()
            , 4, new AckMessageHandler()
            , 5, new StateMessageHandler()
            , 7, new JoinMessageHandler()
            , 9, new RoleChangeMessageHandler()
    );

    public void putSteerMsgToStorage(GameMessage steerMessage) {
        synchronized (steerMsgStorage) {
            steerMsgStorage.add(steerMessage);
        }
    }

    public List<GameMessage> getLastSteerMsgFromStorage() {
        List<GameMessage> lastSteerMsgs = new CopyOnWriteArrayList<>();
        synchronized (steerMsgStorage) {
            lastSteerMsgs.addAll(steerMsgStorage);
            steerMsgStorage.clear();
        }
        return lastSteerMsgs;
    }
}
