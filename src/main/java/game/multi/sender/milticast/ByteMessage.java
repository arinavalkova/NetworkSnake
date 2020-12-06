package game.multi.sender.milticast;

import java.net.InetSocketAddress;

public class ByteMessage {
    private long currentTimeSent;
    private long timeFromFirstSent;
    private final InetSocketAddress socketAddress;
    private final byte[] message;

    public ByteMessage(InetSocketAddress socketAddress, byte[] message) {
        this.currentTimeSent = System.currentTimeMillis();
        this.timeFromFirstSent = 0;
        this.socketAddress = socketAddress;
        this.message = message;
    }

    public void updateForRecent() {
        long currentTimeSent = System.currentTimeMillis();
        long lastWaitTime = currentTimeSent - this.currentTimeSent;
        this.currentTimeSent = currentTimeSent;
        this.timeFromFirstSent += lastWaitTime;
    }

    public long getTimeFromFirstSent() {
        return timeFromFirstSent;
    }

    public long getCurrentTimeSent() {
        return currentTimeSent;
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    public byte[] getMessage() {
        return message;
    }
}
