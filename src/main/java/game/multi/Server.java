package game.multi;

import dto.NodeRole;
import game.multi.receive.ReceiverFactory;
import game.multi.sender.milticast.SenderMulticast;
import graphics.controllers.GameWindowController;

import java.util.ArrayList;

public class Server {
    private static Network network;
    private static ReceiverFactory receiverFactory;
    private static Game game = null;
    private final static SenderMulticast senderMulticast = new SenderMulticast(network);

    public static void startNewGame(GameWindowController controller) {
        DecorateGame decorateGame = new DecorateGame(controller, network);
        decorateGame.decorateNewGame();
        game = decorateGame.getGame();  //if doesn't work create updateGame method
    }

    public static void joinGame(GameWindowController controller, NodeRole nodeRole) {
        DecorateGame decorateGame = new DecorateGame(controller, network);
        decorateGame.decorateJoinGameAs(nodeRole);
        game = decorateGame.getGame();  //if doesn't work create updateGame method
    }

    public static Network getNetwork() {
        return network;
    }

    public static void start() {
        network = new Network();
        receiverFactory = new ReceiverFactory(network, game);
        receiverFactory.start();
    }

    public static void stop() {
        receiverFactory.stop();
        game.stop();
        network.stop();
    }

    public static ArrayList<String> getListOfCurrentGames() { // <-- TO DO
        return null;
    }

    public static SenderMulticast getSenderMulticast() {
        return senderMulticast;
    }
}
