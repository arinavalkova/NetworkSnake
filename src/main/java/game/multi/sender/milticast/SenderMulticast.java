package game.multi.sender.milticast;

import com.google.protobuf.InvalidProtocolBufferException;
import dto.GameMessage;
import game.multi.Network;
import main.TimeOut;

public class SenderMulticast {
    private final static int TIME_OUT = 1000;

    private boolean isWork = true;
    private Network network;
    private byte[] message;
    private Thread sendThread;

    public SenderMulticast(Network network) {
        this.network = network;
        this.sendThread = new Thread(() -> {
            while (isWork) {
                this.network.sendToGroup(message);
                new TimeOut(TIME_OUT).start();
            }
        });
    }

    public void updateGameStateInvite(byte[] message) {
        this.message = message;
        if (sendThread.getState() == Thread.State.NEW) {
            sendThread.start();
        }
    }

    public void stop() {
        isWork = false;
        sendThread = new Thread(() -> {
            while (isWork) {
                network.sendToGroup(message);
                new TimeOut(TIME_OUT).start();
            }
        });
    }
}
