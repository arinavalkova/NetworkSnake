package game.multi.players;

import dto.NodeRole;
import game.multi.Game;
import game.multi.field.CellRole;

public class ViewerPlayer implements Player {
    @Override
    public int play(Game game) {
        if (game.getGameWindowController().getButtonNodeRole() == NodeRole.NORMAL) {
            //Thread starts here ->
            //send to master that i want to join
            //here i am waiting for answer
            //if no answer send too deputy until answer
            //parse gotten message
            //update GameField
            game.setNodeRole(NodeRole.NORMAL);
            //Thread ends here ->
        }
        //waiting getState message
        //update game field
        game.getGameFieldDrawer().redrawField();
        game.getGameFieldDrawer().draw(CellRole.FOOD);
        game.getGameFieldDrawer().draw(CellRole.SNAKE);
        return 0;
    }
}
