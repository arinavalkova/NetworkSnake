package game.multi.receive;

import dto.GameMessage;
import game.multi.Network;
import game.multi.receive.handlers.MessageHandler;
import game.multi.sender.milticast.ByteMessage;

import java.io.IOException;

public class ReceiverMulticast {
    private Network network;
    private final CurrentGames currentGames;
    private boolean isReceiverFromMulticastWork;

    private final Thread receiverFromMulticast = new Thread(() -> {
        while (isReceiverFromMulticastWork) {
            try {
                ByteMessage receivedMessage = network.receiveFromMulticast();
                GameMessage gameMessage = GameMessage.parseFrom(receivedMessage.getMessage());
                getCurrentGames().update(receivedMessage.getSocketAddress(), gameMessage);
            } catch (IOException e) {
                break;
            }
        }
    });

    public ReceiverMulticast(Network network) {
        this.network = network;
        this.currentGames = new CurrentGames();
        this.isReceiverFromMulticastWork = true;
    }

    public void start() {
        receiverFromMulticast.start();
    }

    public void stop() {
        //если я мастер отправить deputy очередь с необработанными сообщениями
        this.isReceiverFromMulticastWork = false;
    }

    public CurrentGames getCurrentGames() {
        return currentGames;
    }
}
