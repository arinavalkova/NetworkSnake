package game.multi.players;

import dto.Direction;
import dto.NodeRole;
import game.multi.Game;
import game.multi.proto.creators.RoleChangeMessageCreator;
import game.multi.proto.creators.SteerMessageCreator;

public class DeputyPlayer implements Player {
    @Override
    public void play(Game game) {
        if (game.getGameWindowController().getButtonNodeRole() == NodeRole.VIEWER) {
            int msg_seq = game.getAndIncMsgSeq();
            game.getConfirmSender().send(
                    msg_seq,
                    new RoleChangeMessageCreator(
                            msg_seq,
                            game.getMy_id(),
                            game.getMaster_id(),
                            NodeRole.VIEWER,
                            null
                    ).getBytes(),
                    game.getMasterSocketAddress()
            );
            game.setNodeRole(NodeRole.VIEWER);
            return;
        }

        Direction moveDirection = game.getKeyController().getKey();
        int msg_seq = game.getAndIncMsgSeq();
        game.getConfirmSender().send(
                msg_seq,
                new SteerMessageCreator(
                        msg_seq,
                        game.getMy_id(),
                        null,
                        moveDirection
                ).getBytes(),
                game.getMasterSocketAddress()
        );
    }
}
