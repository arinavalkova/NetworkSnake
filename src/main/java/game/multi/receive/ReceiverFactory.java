package game.multi.receive;

import com.google.protobuf.InvalidProtocolBufferException;
import dto.GameMessage;
import game.multi.Game;
import game.multi.Network;
import game.multi.proto.parses.ProtoParser;
import game.multi.receive.handlers.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReceiverFactory {
    private Network network;
    private Game game;
    private ProtoParser protoParser;

    private final CurrentGames currentGames;
    private List<byte[]> messagesBuffer;
    private final List<GameMessage> waitingForProcessingMessages;

    private final Thread receiver = new Thread(() -> {
        while (true) {
            messagesBuffer.add(network.receiveFromSocket());
        }
    });

    private final Thread processor = new Thread(() -> {
        while (true) {
            if (messagesBuffer.isEmpty()) {
                continue;
            }
            for (byte[] currentMessage : messagesBuffer) {
                ProtoParser parsedMessage = null;
                try {
                    parsedMessage = new ProtoParser(currentMessage);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
                MessageHandler messageHandler = messageHandlerMap.get(parsedMessage.getType());
                messageHandler.handle(this, parsedMessage.getGameMessage());
            }
        }
    });

    public ReceiverFactory(Network network, Game game) {
        this.network = network;
        this.game = game;
        this.messagesBuffer = new CopyOnWriteArrayList<>();
        this.waitingForProcessingMessages = new CopyOnWriteArrayList<>();
        this.currentGames = new CurrentGames();
        this.protoParser = game.getProtoParser();
    }

    public void start() {
        receiver.start();
        processor.start();
    }

    public void stop() {
        //если я мастер отправить deputy очередь с необработанными сообщениями
        receiver.interrupt();
        processor.interrupt();
    }

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

    public Game getGame() {
        return game;
    }

    public CurrentGames getCurrentGames() {
        return currentGames;
    }

    public List<GameMessage> getWaitingForProcessingMessages() {
        return waitingForProcessingMessages;
    }
}
