package game.multi.players;

import dto.Direction;
import dto.NodeRole;
import game.multi.Game;

public class NormalPlayer implements Player {
    @Override
    public void play(Game game) {
        if (game.getGameWindowController().getButtonNodeRole() == NodeRole.VIEWER) {
            //send to master that my snake ZOMBIE
            game.setNodeRole(NodeRole.VIEWER);
            return;
        }
        Direction moveDirection = game.getKeyController().getKey();
        //send to MASTER this move direction
        //game.getGameWindowController().setPoints(points);
    }
}
