package game.multi;

import dto.GameMessage;
import dto.GameState;
import dto.NodeRole;
import game.multi.proto.creators.JoinMessageCreator;
import game.multi.receive.ReceiverMulticast;
import game.multi.sender.milticast.ByteMessage;
import graphics.controllers.GameWindowController;
import graphics.loaders.SceneController;
import graphics.loaders.WindowNames;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private static final Integer UNKNOWN_SENDER_ID = -1;
    private static Network network;
    private static ReceiverMulticast receiverMulticast;
    private static GamePlay gamePlay = null;
    private static boolean isStopped;

    public static void startNewGame(GameWindowController controller) {
        GameFormer gameFormer = new GameFormer(controller, network);
        gameFormer.decorateNewGame();
        Server.gamePlay = gameFormer.getGame();
    }

    public static String joinGameIfSuccess(NodeRole nodeRole, InetSocketAddress socketAddress, String name) {
        try {
            network.sendToSocket(
                    new JoinMessageCreator(0,
                            nodeRole == NodeRole.VIEWER,
                            name,
                            UNKNOWN_SENDER_ID
                    ).getBytes(),
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
            GameWindowController gameWindowController = SceneController.load(
                    WindowNames.GAME_WINDOW
            ).getController();
            gameWindowController.setChangeRoleButton(nodeRole);
            int my_id = ackOrErrorFromServer.getReceiverId();
            ByteMessage gameStateFromServer = network.receiveFromSocket();
            GameMessage gameStateMessage = GameMessage.parseFrom(gameStateFromServer.getMessage());
            GameState gameState = gameStateMessage.getState().getState();
            joinGame(gameWindowController,
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
        GameFormer gameFormer = new GameFormer(controller, network);
        gameFormer.decorateJoinGameAs(gameState, nodeRole, my_id, masterSocketAddress);
        Server.gamePlay = gameFormer.getGame();
    }

    public static Network getNetwork() {
        return network;
    }

    public static void start() {
        isStopped = false;
        network = new Network();
        receiverMulticast = new ReceiverMulticast(network);
        receiverMulticast.start();
    }

    public static void stop() {
        isStopped = true;
        receiverMulticast.stop();
        if (gamePlay != null) {
            gamePlay.stop();
        }
        network.stop();
    }

    public static void stopGamePlay() {
        gamePlay.stop();
    }

    public static ReceiverMulticast getReceiverMulticast() {
        return receiverMulticast;
    }

    public static boolean isStopped() {
        return isStopped;
    }

    public static GamePlay getGamePlay() {
        return gamePlay;
    }

    public static void deleteFromCurrentGames(GameState gameState) {
        receiverMulticast.getCurrentGames().deleteGame(gameState);
    }
}
