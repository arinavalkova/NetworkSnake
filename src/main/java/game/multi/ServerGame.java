package game.multi;

import graphics.controllers.GameWindowController;

public class ServerGame {
    private static GameWindowController gameWindowController;       // может тоже не нужен здесь
    private static final DataStream dataStream = new DataStream();

    public static void startServer() { }                            // может кстати вообще не понадобится

    public static void startNewGame(GameWindowController controller) {
        ServerGame.gameWindowController = controller;
    }

    public static String getCurrentGames() {
        return new String(dataStream.receiveFromMulticast());
    }

    public static void joinGame(GameWindowController controller) {
        ServerGame.gameWindowController = controller;
    }

    public static DataStream getDataStream() {
        return dataStream;
    }
}
