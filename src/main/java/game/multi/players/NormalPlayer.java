package game.multi.players;

import dto.NodeRole;
import game.multi.Game;
import game.multi.field.CellRole;
import game.multi.snake.mover.MoveDirection;

public class NormalPlayer implements Player {
    @Override
    public int play(Game game) {
        if (game.getGameWindowController().getButtonNodeRole() == NodeRole.VIEWER) {
            //send to master that my snake ZOMBIE
            //here i am waiting for answer
            //if no answer send too deputy until answer
            game.setNodeRole(NodeRole.VIEWER);
            return 0;
        }
        MoveDirection moveDirection = game.getKeyController().getKey();
        //send to MASTER this move direction
        //wait info about this moveDirection
        //update field
        //if game over return -1
        //get also points
        //game.getGameWindowController().setPoints(points);
        game.getGameFieldDrawer().redrawField();
        game.getGameFieldDrawer().draw(CellRole.FOOD);
        game.getGameFieldDrawer().draw(CellRole.SNAKE);
        return 0;
    }
}
