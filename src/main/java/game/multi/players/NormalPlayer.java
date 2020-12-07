package game.multi.players;

import dto.Direction;
import dto.NodeRole;
import game.multi.GamePlay;
import game.multi.Server;
import game.multi.proto.creators.SteerMessageCreator;
import game.multi.proto.viewers.GamePlayersViewer;

public class NormalPlayer implements Player {
    @Override
    public void play(GamePlay gamePlay) {
        if (gamePlay.getGameWindowController().getButtonNodeRole() == NodeRole.VIEWER) {
            //send to master that my snake ZOMBIE
            gamePlay.setNodeRole(NodeRole.VIEWER);
        }
    }
}
