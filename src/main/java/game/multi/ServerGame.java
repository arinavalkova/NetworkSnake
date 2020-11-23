package game.multi;

import graphics.controllers.GameWindowController;
import graphics.data.NewGameWindowData;

import java.io.IOException;
import java.net.DatagramPacket;

public class ServerGame {
    private static GameWindowController gameWindowController;
    private static final DataStream dataStream = new DataStream();

    public static void startServer() {

    }

    public static void startNewGame(GameWindowController controller) {
        ServerGame.gameWindowController = controller;

        /* TEST */
        Thread sendToGroupThread = new Thread(() -> {
            while (true) {
                try {
                    dataStream.sendToGroup("Hello".getBytes());
                    Thread.sleep(1000);
                    dataStream.sendToGroup("By".getBytes());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        sendToGroupThread.start();
    }

    public static String getCurrentGames() {
        /* TEST */
        return new String(dataStream.receiveFromMulticast());
    }

    public static void joinGame(GameWindowController controller) {
        ServerGame.gameWindowController = controller;
    }

    public static DataStream getDataStream() {
        return dataStream;
    }
}
