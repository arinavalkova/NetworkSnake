package game.multi.receive.unicast;

import game.multi.Network;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UnicastMessagesStorage {
    private final Network network;
    private final List<String/* Messages */> receivedMessagesList;

    public UnicastMessagesStorage(Network network) {
        this.network = network;
        receivedMessagesList = new CopyOnWriteArrayList<>();
    }

    public void start() {
        Thread receiveMessagesThread = new Thread(() -> {
            while (true) {
                receivedMessagesList.add(new String(network.receiveFromSocket()));
                //change string to packet
            }
        });
        receiveMessagesThread.start();
    }

    public String takeReceivedMessage() {
        String receivedMessage = receivedMessagesList.get(0);
        receivedMessagesList.remove(0);
        return receivedMessage;
    }
}
