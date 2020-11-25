package game.multi.players;

import dto.NodeRole;
import game.multi.Game;

public class DeputyPlayer implements Player {
    @Override
    public int play(Game game) {
        if (game.getGameWindowController().getButtonNodeRole() == NodeRole.VIEWER) {
            //become VIEWER
            //send that my snake is ZOMBIE
            //collect messages from MASTER when MASTER become ZOMBIE and mark as valuable
            //send new DEPUTY all valuable messages
        }
        //if gameover return -1
        return 0;
    }
}
