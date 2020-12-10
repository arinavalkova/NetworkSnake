package game.multi.receive.handlers;

import dto.GameMessage;
import dto.NodeRole;
import game.multi.GamePlay;
import game.multi.proto.renovators.GamePlayerRenovator;
import game.multi.proto.renovators.GamePlayersRenovator;
import game.multi.proto.renovators.SnakeRenovator;
import game.multi.proto.viewers.GamePlayerViewer;

import java.net.InetSocketAddress;

public class RoleChangeMessageHandler implements MessageHandler {
    @Override
    public void handle(InetSocketAddress socketAddress, GamePlay gamePlay, GameMessage currentMessage) {
        int playerId = currentMessage.getSenderId();
        if (currentMessage.getRoleChange().getSenderRole() == NodeRole.VIEWER) {
            NodeRole oldNodeRole = new GamePlayerViewer(gamePlay.getGameState())
                    .getNodeRoleById(playerId);
            new GamePlayerRenovator(gamePlay).playerBecomeViewer(playerId);
            if (oldNodeRole == NodeRole.DEPUTY) {
                new GamePlayersRenovator(gamePlay).findNewDeputy();
            }
            new SnakeRenovator(gamePlay).makeSnakeZombie(playerId);
        }
    }
}
