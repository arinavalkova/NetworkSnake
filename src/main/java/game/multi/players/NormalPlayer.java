package game.multi.players;

import dto.NodeRole;
import game.multi.Game;
import game.multi.field.CellRole;
import game.multi.snake.mover.MoveDirection;

public class NormalPlayer implements Player {
    @Override
    public void play(Game game) {
        if (game.getGameWindowController().getButtonNodeRole() == NodeRole.VIEWER) {
            //send to master that my snake ZOMBIE
            game.setNodeRole(NodeRole.VIEWER);
            return;
        }
        MoveDirection moveDirection = game.getKeyController().getKey();
        //send to MASTER this move direction
        //game.getGameWindowController().setPoints(points);
    }
}
