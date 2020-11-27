package game.multi.receive;

import game.multi.Game;
import game.multi.Network;
import game.multi.receive.handlers.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReceiverFactory {
    private Network network;
    private Game game;

    private final CurrentGames currentGames;
    private Map<Integer, String> messagesBuffer;
    private Map<Integer, String> steerMessages; // from whom was gotten this message

    private final Thread receiver = new Thread(() -> {
        while (true) {
            /* <-- TO DO */
            String message = new String(network.receiveFromSocket());
            messagesBuffer.put(0, message);
        }
    });

    private final Thread processor = new Thread(() -> {
        while (true) {
            if (messagesBuffer.isEmpty()) {
                continue;
            }
            /* <-- TO DO */
            Messages messageType = Messages.ERROR;
            String message = "MEs";
            //take from messageBuffer messageType

            MessageHandler messageHandler = messageHandlerMap.get(/*TEST*/messageType);
            messageHandler.handle(this);
        }
    });

    public ReceiverFactory(Network network, Game game) {
        this.network = network;
        this.game = game;
        this.messagesBuffer = new ConcurrentHashMap<>();
        this.steerMessages = new ConcurrentHashMap<>();
        this.currentGames = new CurrentGames();
    }

    public void start() {
        receiver.start();
        processor.start();
    }

    public void stop() {
        //если я мастер отправить deputy очередь с сообщениями с желанием повернуть
        receiver.interrupt();
        processor.interrupt();
    }

    //синхронизированный метод для получения последних сообщений и удаления их

    private static final Map<Messages, MessageHandler> messageHandlerMap = Map.of(
            Messages.ACK, new AckMessage(),
            Messages.ANNOUNCMENT, new AnnouncmentMessage(),
            Messages.ERROR, new ErrorMessage(),
            Messages.JOIN, new JoinMessage(),
            Messages.PING, new PingMessage(),
            Messages.ROLE_CHANGE, new RoleChangeMessage(),
            Messages.STATE, new StateMessage(),
            Messages.STEER, new SteerMessage()
    );

    public Network getNetwork() {
        return network;
    }

    public Game getGame() {
        return game;
    }

    public Map<Integer, String> getMessagesBuffer() {
        return messagesBuffer;
    }

    public Map<Integer, String> getSteerMessages() {
        return steerMessages;
    }

    public CurrentGames getCurrentGames() {
        return currentGames;
    }
}
