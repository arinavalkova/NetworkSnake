package game.multi.proto.creators;

import dto.GameMessage;

public class JoinMessageCreator {
    private final Integer msg_seq;
    private final Integer sender_id;
    private final Integer receiver_id;

    public JoinMessageCreator(Integer msg_seq, Integer sender_id, Integer receiver_id) {
        this.msg_seq = msg_seq;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
    }

    public byte[] getBytes() {
        return null;
    }
}
