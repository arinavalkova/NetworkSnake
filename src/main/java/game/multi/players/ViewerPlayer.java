package game.multi.players;

import dto.NodeRole;
import game.multi.Game;
import game.multi.field.CellRole;

public class ViewerPlayer implements Player {
    @Override
    public int play(Game game) {
        if (game.getGameWindowController().getButtonNodeRole() == NodeRole.NORMAL) {
            //send to master that i want to join
            game.setNodeRole(NodeRole.NORMAL);
        }

        game.getGameFieldDrawer().redrawField();
        game.getGameFieldDrawer().draw(CellRole.FOOD);
        game.getGameFieldDrawer().draw(CellRole.SNAKE);
        return 0;
    }
}
