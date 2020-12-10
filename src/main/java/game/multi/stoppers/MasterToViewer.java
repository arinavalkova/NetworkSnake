package game.multi.stoppers;

import dto.GameMessage;
import dto.NodeRole;
import game.multi.GamePlay;
import game.multi.Server;
import game.multi.proto.creators.RoleChangeMessageCreator;

import java.util.List;

public class MasterToViewer implements ToViewer {
    @Override
    public void start(GamePlay gamePlay) {
        Server.getNetwork().sendToSocket(
                new RoleChangeMessageCreator(
                        gamePlay.getAndIncMsgSeq(),
                        gamePlay.getMy_id(),
                        gamePlay.getDeputy_id(),
                        null,
                        NodeRole.MASTER
                ).getBytes(),
                gamePlay.getDeputySocketAddress()
        );
        sendAllMySteerMsgsFromStorage(gamePlay);
        gamePlay.getMasterTimer().stop();
        gamePlay.setMyNodeRole(NodeRole.VIEWER);
    }

    private void sendAllMySteerMsgsFromStorage(GamePlay gamePlay) {
        List<GameMessage> steerMsgsList = gamePlay.getSteerMessages();
        for (GameMessage currentGameMessage : steerMsgsList) {
            Server.getNetwork().sendToSocket(currentGameMessage.toByteArray(), gamePlay.getDeputySocketAddress());
        }
    }
}
