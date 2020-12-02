package game.multi.players;

import dto.NodeRole;
import game.multi.GamePlay;

public class ViewerPlayer implements Player {
    @Override
    public void play(GamePlay gamePlay) {
        if (gamePlay.getGameWindowController().getButtonNodeRole() == NodeRole.NORMAL) {
            //send to master that i want to join
            gamePlay.setNodeRole(NodeRole.NORMAL);
        }
    }
}