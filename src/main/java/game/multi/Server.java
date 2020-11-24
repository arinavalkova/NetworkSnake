package game.multi;

import dto.NodeRole;
import graphics.controllers.GameWindowController;

public class Server {
    private static final Network network = new Network();

    public static void startNewGame(GameWindowController controller) {
        DecorateGame decorateGame = new DecorateGame(controller, network);
        decorateGame.decorateNewGame();
    }

//    public static String getCurrentGames() {                 <-- at that moment it is unless
//        return new String(network.receiveFromMulticast());
//    }

    public static void joinGame(GameWindowController controller, NodeRole nodeRole) {
        DecorateGame decorateGame = new DecorateGame(controller, network);
        decorateGame.decorateJoinGameAs(nodeRole);
    }

    public static Network getNetwork() {
        return network;
    }
}
