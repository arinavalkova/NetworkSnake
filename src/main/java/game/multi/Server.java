package game.multi;

import dto.GameMessage;
import dto.GameState;
import dto.NodeRole;
import game.multi.proto.creators.JoinMessageCreator;
import game.multi.receive.ReceiverFactory;
import game.multi.sender.milticast.ByteMessage;
import game.multi.sender.milticast.SenderMulticast;
import graphics.controllers.GameWindowController;
import graphics.loaders.SceneController;
import graphics.loaders.WindowNames;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Server {
    private static Network network;
    private static ReceiverFactory receiverFactory;
    private static GamePlay gamePlay = null;
    private static boolean isStopped;

    public static void startNewGame(GameWindowController controller) {
        GameDecorator gameDecorator = new GameDecorator(controller, network);
        gameDecorator.decorateNewGame();
        Server.gamePlay = gameDecorator.getGame();
        receiverFactory.setGamePlay(Server.gamePlay);
    }

    public static String joinGameIfSuccess(NodeRole nodeRole, InetSocketAddress socketAddress, String name) {
        try {
            network.sendToSocket(
                    new JoinMessageCreator(0, false, name).getBytes(),
                    socketAddress
            );
            boolean isAckMessage = false;
            ByteMessage receiveFromSocketMessage = null;
            GameMessage ackOrErrorFromServer = null;
            while (!isAckMessage) {
                receiveFromSocketMessage = network.receiveFromSocket();
                ackOrErrorFromServer = GameMessage.parseFrom(receiveFromSocketMessage.getMessage());
                isAckMessage = ackOrErrorFromServer.hasAck() | ackOrErrorFromServer.hasError();
            }
            if (ackOrErrorFromServer.hasError()) {
                return ackOrErrorFromServer.getError().getErrorMessage();
            }
            int my_id = ackOrErrorFromServer.getReceiverId();
            ByteMessage gameStateFromServer = network.receiveFromSocket();
            GameMessage gameStateMessage = GameMessage.parseFrom(gameStateFromServer.getMessage());
            GameState gameState = gameStateMessage.getState().getState();
            joinGame(SceneController.load(
                    WindowNames.GAME_WINDOW
                    ).getController(),
                    nodeRole,
                    gameState,
                    my_id,
                    receiveFromSocketMessage.getSocketAddress()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void joinGame(GameWindowController controller
            , NodeRole nodeRole
            , GameState gameState
            , int my_id
            , InetSocketAddress masterSocketAddress
    ) {
        GameDecorator gameDecorator = new GameDecorator(controller, network);
        gameDecorator.decorateJoinGameAs(gameState, nodeRole, my_id, masterSocketAddress);
        Server.gamePlay = gameDecorator.getGame();
        receiverFactory.setGamePlay(Server.gamePlay);
    }

    public static Network getNetwork() {
        return network;
    }

    public static void start() {
        isStopped = false;
        network = new Network();
        receiverFactory = new ReceiverFactory(network, gamePlay);
        receiverFactory.start();
    }

    public static void stop() {
        isStopped = true;
        receiverFactory.stop();
        if (gamePlay != null) {
            gamePlay.stop();
        }
        network.stop();
    }

    public static void stopGamePlay() {
        gamePlay.stop();
    }

    public static ReceiverFactory getReceiverFactory() {
        return receiverFactory;
    }

    public static boolean isStopped() {
        return isStopped;
    }

    public static GamePlay getGamePlay() {
        return gamePlay;
    }

    public static void deleteFromCurrentGames(GameState gameState) {
        receiverFactory.getCurrentGames().deleteGame(gameState);
    }
}
