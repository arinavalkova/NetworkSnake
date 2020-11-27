package game.multi.sender.milticast;

import game.multi.Network;
import main.TimeOut;

public class SenderMulticast {
    private final static int TIME_OUT = 1000;

    private boolean isWork = true;
    private Network network;
    private byte[] message;

    private Thread sendThread = new Thread(() -> {
        while (isWork) {
            network.sendToGroup(message);
            new TimeOut(TIME_OUT).start();
        }
    });

    public SenderMulticast(Network network) {
        this.network = network;
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
