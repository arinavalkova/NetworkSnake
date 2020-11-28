package game.multi.proto.creators;

import dto.Direction;
import dto.GameMessage;

public class SteerMessageCreator {
    private final Integer msg_seq;
    private final Integer sender_id;
    private final Integer receiver_id;
    private final Direction direction;

    public SteerMessageCreator(Integer msg_seq, Integer sender_id, Integer receiver_id, Direction direction) {
        this.msg_seq = msg_seq;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.direction = direction;
    }

    public byte[] getBytes() {
        GameMessage.SteerMsg.Builder steerMessageBuilder = GameMessage.SteerMsg.newBuilder();
        steerMessageBuilder.setDirection(direction);

        GameMessage.Builder gameMessageBuilder = GameMessage.newBuilder();
        gameMessageBuilder.setMsgSeq(msg_seq);
        if (sender_id != null) {
            gameMessageBuilder.setSenderId(sender_id);
        }
        if (receiver_id != null) {
            gameMessageBuilder.setReceiverId(receiver_id);
        }
        gameMessageBuilder.setSteer(steerMessageBuilder.build());

        return gameMessageBuilder.build().toByteArray();
    }
}
