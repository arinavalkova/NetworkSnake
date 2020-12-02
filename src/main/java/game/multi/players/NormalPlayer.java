package game.multi.players;

import dto.Direction;
import dto.NodeRole;
import game.multi.GamePlay;

public class NormalPlayer implements Player {
    @Override
    public void play(GamePlay gamePlay) {
        if (gamePlay.getGameWindowController().getButtonNodeRole() == NodeRole.VIEWER) {
            //send to master that my snake ZOMBIE
            gamePlay.setNodeRole(NodeRole.VIEWER);
            return;
        }
        Direction moveDirection = gamePlay.getKeyController().getKey();
        //send to MASTER this move direction
        //game.getGameWindowController().setPoints(points);
    }
}
