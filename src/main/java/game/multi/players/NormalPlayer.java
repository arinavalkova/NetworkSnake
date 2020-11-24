package game.multi.players;

import game.multi.Game;
import game.multi.field.CellRole;
import game.multi.snake.mover.MoveDirection;

public class NormalPlayer implements Player {
    @Override
    public int play(Game game) {
        MoveDirection moveDirection = game.getKeyController().getKey();
        //send to MASTER this move direction
        //get info about this moveDirection
        //get also points
        game.getGameWindowController().setPoints(points);
        game.getGameFieldDrawer().redrawField();
        game.getGameFieldDrawer().draw(CellRole.FOOD);
        game.getGameFieldDrawer().draw(CellRole.SNAKE);
        return 0;
    }
}
