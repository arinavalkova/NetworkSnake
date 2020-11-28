package game.multi.proto.creators;

import dto.GameMessage;
import dto.NodeRole;

public class RoleChangeMessageCreator {
    private final Integer msg_seq;
    private final Integer sender_id;
    private final Integer receiver_id;
    private final NodeRole receiver_role;
    private final NodeRole sender_role;

    public RoleChangeMessageCreator(Integer msg_seq, Integer sender_id, Integer receiver_id,
                                    NodeRole sender_role, NodeRole receiver_role) {
        this.msg_seq = msg_seq;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.receiver_role = receiver_role;
        this.sender_role = sender_role;
    }

    public byte[] getBytes() {
        GameMessage.RoleChangeMsg.Builder roleChangeMessageBuilder = GameMessage.RoleChangeMsg.newBuilder();
        if (receiver_role != null) {
            roleChangeMessageBuilder.setReceiverRole(receiver_role);
        }
        if (sender_role != null) {
            roleChangeMessageBuilder.setSenderRole(sender_role);
        }

        GameMessage.Builder gameMessageBuilder = GameMessage.newBuilder();
        gameMessageBuilder.setMsgSeq(msg_seq);
        if (sender_id != null) {
            gameMessageBuilder.setSenderId(sender_id);
        }
        if (receiver_id != null) {
            gameMessageBuilder.setReceiverId(receiver_id);
        }
        gameMessageBuilder.setRoleChange(roleChangeMessageBuilder.build());

        return gameMessageBuilder.build().toByteArray();
    }
}
