package game.multi.proto.creators;

import dto.GameMessage;

public class AckMessageCreator {
    private final GameMessage ackMessage;
    public AckMessageCreator(Integer msg_seq, Integer receiver_id) {
        GameMessage.AckMsg ackMsg = GameMessage.AckMsg.newBuilder()
                .build();
        this.ackMessage = GameMessage.newBuilder()
                .setMsgSeq(msg_seq)
                .setReceiverId(receiver_id)
                .setAck(ackMsg)
                .build();
    }

    public byte[] getBytes() {
        return ackMessage.toByteArray();
    }
}
