package game.multi.players;

import dto.Direction;
import dto.NodeRole;
import game.multi.GamePlay;
import game.multi.proto.creators.RoleChangeMessageCreator;
import game.multi.proto.creators.SteerMessageCreator;

public class DeputyPlayer implements Player {
    @Override
    public void play(GamePlay gamePlay) {
        if (gamePlay.getGameWindowController().getButtonNodeRole() == NodeRole.VIEWER) {
            int msg_seq = gamePlay.getAndIncMsgSeq();
//            gamePlay.getConfirmSender().send(
//                    msg_seq,
//                    new RoleChangeMessageCreator(
//                            msg_seq,
//                            gamePlay.getMy_id(),
//                            gamePlay.getMaster_id(),
//                            NodeRole.VIEWER,
//                            null
//                    ).getBytes(),
//                    gamePlay.getMasterSocketAddress()
//            );
            gamePlay.setNodeRole(NodeRole.VIEWER);
        }
//        gamePlay.getConfirmSender().send(
//                msg_seq,
//                new SteerMessageCreator(
//                        msg_seq,
//                        gamePlay.getMy_id(),
//                        null,
//                        moveDirection
//                ).getBytes(),
//                gamePlay.getMasterSocketAddress()
//        );
    }
}
