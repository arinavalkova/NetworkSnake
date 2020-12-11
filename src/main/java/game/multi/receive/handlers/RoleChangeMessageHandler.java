package game.multi.receive.handlers;

import dto.GameMessage;
import dto.NodeRole;
import game.multi.GamePlay;
import game.multi.Server;
import game.multi.proto.creators.AckMessageCreator;
import game.multi.proto.creators.AnnouncmentMessageCreator;
import game.multi.proto.renovators.GamePlayerRenovator;
import game.multi.proto.renovators.GamePlayersRenovator;
import game.multi.proto.renovators.GameStateRenovator;
import game.multi.proto.renovators.SnakeRenovator;
import game.multi.proto.viewers.GamePlayerViewer;
import org.w3c.dom.Node;

import java.net.InetSocketAddress;

public class RoleChangeMessageHandler implements MessageHandler {
    @Override
    public void handle(InetSocketAddress socketAddress, GamePlay gamePlay, GameMessage currentMessage) {
        int playerId = currentMessage.getSenderId();
        if (currentMessage.getRoleChange().getSenderRole() == NodeRole.VIEWER) {
            NodeRole oldNodeRole = new GamePlayerViewer(gamePlay.getGameState())
                    .getNodeRoleById(playerId);
            String playerName = new GamePlayerViewer(gamePlay.getGameState()).getPlayerName(playerId);
            GamePlayersRenovator gamePlayersRenovator = new GamePlayersRenovator(gamePlay);
            gamePlayersRenovator.deletePlayer(playerId);
            gamePlay.updateGameState(gamePlayersRenovator.getGameState());
            GameStateRenovator gameStateRenovator = new GameStateRenovator(gamePlay);
            int newPlayerId = gameStateRenovator.addViewerPlayer(socketAddress,
                    playerName);
            gamePlay.updateGameState(gameStateRenovator.getGameState());

            Server.getNetwork().sendToSocket(
                    new AckMessageCreator(
                            (int) currentMessage.getMsgSeq(),
                            gamePlay.getMy_id(),
                            newPlayerId).getBytes(),
                    socketAddress
            );
            if (oldNodeRole == NodeRole.DEPUTY) {
                new GamePlayersRenovator(gamePlay).findNewDeputy();
            }
            new SnakeRenovator(gamePlay).makeSnakeZombie(playerId);
        }

        if (currentMessage.getRoleChange().getReceiverRole() == NodeRole.MASTER &&
                gamePlay.getMyNodeRole() == NodeRole.DEPUTY) {
            String playerName = new GamePlayerViewer(gamePlay.getGameState()).getPlayerName(playerId);
            GamePlayersRenovator gamePlayersRenovator = new GamePlayersRenovator(gamePlay);
            gamePlayersRenovator.deletePlayer(playerId);
            gamePlay.updateGameState(gamePlayersRenovator.getGameState());
            GameStateRenovator gameStateRenovator = new GameStateRenovator(gamePlay);
            int newPlayerId = gameStateRenovator.addViewerPlayer(socketAddress,
                    playerName);
            gamePlay.updateGameState(gameStateRenovator.getGameState());
            Server.getNetwork().sendToSocket(
                    new AckMessageCreator(
                            (int) currentMessage.getMsgSeq(),
                            gamePlay.getMy_id(),
                            newPlayerId).getBytes(),
                    socketAddress
            );
            gamePlay.setMyNodeRole(NodeRole.MASTER);
            SnakeRenovator snakeRenovator = new SnakeRenovator(gamePlay);
            snakeRenovator.makeSnakeZombie(playerId);
            gamePlay.updateGameState(snakeRenovator.getGameState());
            gamePlay.getMasterTimer().start();
            gamePlay.getSenderMulticast().updateGameStateInvite(
                    new AnnouncmentMessageCreator(
                            gamePlay.getAndIncMsgSeq()
                            , gamePlay.getGameState()
                    ).getBytes()
            );
            gamePlay.setIssuedId();
        }

        if (currentMessage.getRoleChange().getReceiverRole() == NodeRole.VIEWER) {
            gamePlay.getGameWindowController().setChangeRoleButton(NodeRole.VIEWER);
        }
    }
}