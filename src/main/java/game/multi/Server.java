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
    private static SenderMulticast senderMulticast;
    private static boolean isStopped;

    public static void startNewGame(GameWindowController controller) {
        GameDecorator gameDecorator = new GameDecorator(controller, network);
        gameDecorator.decorateNewGame();
        Server.gamePlay = gameDecorator.getGame();
    }

    public static void joinGame(GameWindowController controller, NodeRole nodeRole) {
        GameDecorator gameDecorator = new GameDecorator(controller, network);
        gameDecorator.decorateJoinGameAs(nodeRole);
        Server.gamePlay = gameDecorator.getGame();
    }

    public static Network getNetwork() {
        return network;
    }

    public static void start() {
        isStopped = false;
        network = new Network();
        senderMulticast = new SenderMulticast(network);
        receiverFactory = new ReceiverFactory(network, gamePlay);
        receiverFactory.start();
    }

    public static void stop() {
        isStopped = true;
        senderMulticast.stop();
        receiverFactory.stop();
        if (gamePlay != null) {
            gamePlay.stop();
        }
        network.stop();
    }

    public static SenderMulticast getSenderMulticast() {
        return senderMulticast;
    }

    public static ReceiverFactory getReceiverFactory() {
        return receiverFactory;
    }

    public static boolean isStopped() {
        return isStopped;
    }
}
