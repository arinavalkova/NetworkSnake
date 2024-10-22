package game.multi.sender.milticast;

import game.multi.GamePlay;
import game.multi.Network;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfirmSender {
    private Network network;
    private Long ping_delay_ms;
    private Long node_timeout_ms;
    private Map<Integer, ByteMessage> unconfirmedMessages;

    private final Thread checkConfirmThread = new Thread(() -> {
        Iterator<Map.Entry<Integer, ByteMessage>> entries;
        while (true) {
            synchronized (unconfirmedMessages) {
                if (unconfirmedMessages.isEmpty()) {
                    continue;
                }
                entries = unconfirmedMessages.entrySet().iterator();
            }
            while (entries.hasNext()) {
                Map.Entry<Integer, ByteMessage> entry = entries.next();
                if (entry.getValue().getTimeFromFirstSent() > node_timeout_ms) {

                }
                if (System.currentTimeMillis() - entry.getValue().getCurrentTimeSent() > ping_delay_ms) {
                    entry.getValue().updateForRecent();
                    network.sendToSocket(entry.getValue().getMessage(), entry.getValue().getSocketAddress());
                }
            }
        }
    });

    public ConfirmSender(int ping_delay_ms, int node_timeout_ms, Network network, GamePlay gamePlay) {
        this.network = network;
        this.ping_delay_ms = (long) ping_delay_ms;
        this.node_timeout_ms = (long) node_timeout_ms;
        this.unconfirmedMessages = new ConcurrentHashMap<>();
        checkConfirmThread.start();
    }

    public void send(int msg_seq, byte[] message, InetSocketAddress socketAddress) {
        addMessageForWaitingConfirming(msg_seq, message, socketAddress);
        network.sendToSocket(message, socketAddress);
    }

    private void addMessageForWaitingConfirming(int messageSeq, byte[] message, InetSocketAddress socketAddress) {
        synchronized (unconfirmedMessages) {
            unconfirmedMessages.put(messageSeq, new ByteMessage(socketAddress, message));
        }
    }

    public void confirmMessage(long messageSeq) {
        synchronized (unconfirmedMessages) {
            unconfirmedMessages.remove(messageSeq);
        }
    }

    public void stop() {
        checkConfirmThread.interrupt();
    }
}
