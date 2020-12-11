package game.multi.stoppers;

import dto.GameMessage;
import dto.NodeRole;
import game.multi.GamePlay;
import game.multi.Server;
import game.multi.proto.creators.RoleChangeMessageCreator;

import java.net.InetSocketAddress;
import java.util.List;

public class MasterToViewer implements ToViewer {
    private final String END_OF_GAME = "End of game";
    @Override
    public void start(GamePlay gamePlay) {
        InetSocketAddress deputySocketAddress = gamePlay.getDeputySocketAddress();
        if (deputySocketAddress == null) {
            gamePlay.getGameWindowController().setInfoField(END_OF_GAME);
        } else {
            Server.getNetwork().sendToSocket(
                    new RoleChangeMessageCreator(
                            gamePlay.getAndIncMsgSeq(),
                            gamePlay.getMy_id(),
                            gamePlay.getDeputy_id(),
                            null,
                            NodeRole.MASTER
                    ).getBytes(),
                    deputySocketAddress
            );
        }
        sendAllMySteerMsgsFromStorage(gamePlay);
        gamePlay.getMasterTimer().stop();
        gamePlay.getSenderMulticast().stop();
        gamePlay.setMyNodeRole(NodeRole.VIEWER);
    }

    private void sendAllMySteerMsgsFromStorage(GamePlay gamePlay) {
        List<GameMessage> steerMsgsList = gamePlay.getSteerMessages();
        for (GameMessage currentGameMessage : steerMsgsList) {
            Server.getNetwork().sendToSocket(currentGameMessage.toByteArray(), gamePlay.getDeputySocketAddress());
        }
    }
}
