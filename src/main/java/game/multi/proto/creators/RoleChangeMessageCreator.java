package game.multi.proto.creators;

import dto.GameMessage;
import dto.NodeRole;

public class RoleChangeMessageCreator {
    private final GameMessage roleChangeMessage;

    public RoleChangeMessageCreator(Integer msg_seq, Integer sender_id, Integer receiver_id,
                                    NodeRole sender_role, NodeRole receiver_role) {
        GameMessage.RoleChangeMsg.Builder roleChangeMsgBuilder = GameMessage.RoleChangeMsg.newBuilder();
        if (sender_role != null) {
            roleChangeMsgBuilder.setSenderRole(sender_role);
        }
        if (receiver_id != null) {
            roleChangeMsgBuilder.setReceiverRole(receiver_role);
        }
        GameMessage.Builder roleChangeMessageBuilder = GameMessage.newBuilder()
                .setMsgSeq(msg_seq)
                .setRoleChange(roleChangeMsgBuilder.build());
        if (sender_id != null) {
            roleChangeMessageBuilder.setSenderId(sender_id);
        }
        if (receiver_id != null) {
            roleChangeMessageBuilder.setReceiverId(receiver_id);
        }
        this.roleChangeMessage = roleChangeMessageBuilder.build();
    }

    public byte[] getBytes() {
        return roleChangeMessage.toByteArray();
    }
}
