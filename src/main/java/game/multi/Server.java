package game.multi;

import dto.NodeRole;
import game.multi.receive.ReceiverFactory;
import game.multi.sender.milticast.SenderMulticast;
import graphics.controllers.GameWindowController;

import java.util.ArrayList;

public class Server {
    private static Network network;
    private static ReceiverFactory receiverFactory;
    private static GamePlay gamePlay = null;
    private final static SenderMulticast senderMulticast = new SenderMulticast(network);

    public static void startNewGame(GameWindowController controller) {
        GameDecorator gameDecorator = new GameDecorator(controller, network);
        gameDecorator.decorateNewGame();
        gamePlay = gameDecorator.getGame();  //if doesn't work create updateGame method
    }

    public static void joinGame(GameWindowController controller, NodeRole nodeRole) {
        GameDecorator gameDecorator = new GameDecorator(controller, network);
        gameDecorator.decorateJoinGameAs(nodeRole);
        gamePlay = gameDecorator.getGame();  //if doesn't work create updateGame method
    }

    public static Network getNetwork() {
        return network;
    }

    public static void start() {
        network = new Network();
        receiverFactory = new ReceiverFactory(network, gamePlay);
        receiverFactory.start();
    }

    public static void stop() {
        receiverFactory.stop();
        gamePlay.stop();                  //check this
        network.stop();
    }

    public static ArrayList<String> getListOfCurrentGames() { // <-- TO DO
        return null;
    }

    public static SenderMulticast getSenderMulticast() {
        return senderMulticast;
    }

    public static ReceiverFactory getReceiverFactory() {
        return receiverFactory;
    }
}
