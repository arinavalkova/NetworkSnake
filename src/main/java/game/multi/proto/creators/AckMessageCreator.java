package game.multi.proto.creators;

import dto.GameMessage;

public class AckMessageCreator {
    private final Integer msg_seq;
    private final Integer sender_id;
    private final Integer receiver_id;

    public AckMessageCreator(Integer msg_seq, Integer sender_id, Integer receiver_id) {
        this.msg_seq = msg_seq;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
    }

    public byte[] getBytes() {
        GameMessage.Builder gameMessageBuilder = GameMessage.newBuilder();
        gameMessageBuilder.setMsgSeq(msg_seq);
        if (sender_id != null) {
            gameMessageBuilder.setSenderId(sender_id);
        }
        if (receiver_id != null) {
            gameMessageBuilder.setReceiverId(receiver_id);
        }
        gameMessageBuilder.setAck(GameMessage.AckMsg.newBuilder().build()); //check if this work
        return gameMessageBuilder.build().toByteArray();
    }
}
