package game.multi.stoppers;

import dto.NodeRole;
import game.multi.GamePlay;
import game.multi.Server;
import game.multi.proto.creators.RoleChangeMessageCreator;

public class NormalToViewer implements ToViewer {
    @Override
    public void start(GamePlay gamePlay) {
        Server.getNetwork().sendToSocket(
                new RoleChangeMessageCreator(
                        gamePlay.getAndIncMsgSeq(),
                        gamePlay.getMy_id(),
                        null,
                        NodeRole.VIEWER,
                        null
                ).getBytes(),
                gamePlay.getMasterSocketAddress()
        );
        gamePlay.setMyNodeRole(NodeRole.VIEWER);
    }
}
