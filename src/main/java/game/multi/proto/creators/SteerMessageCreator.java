package game.multi.proto.creators;

import dto.Direction;
import dto.GameMessage;

public class SteerMessageCreator {
    private final GameMessage steerMsg;

    public SteerMessageCreator(Integer msg_seq, Integer my_id, Direction direction) {
        System.out.println(my_id + " " + direction);
        GameMessage.SteerMsg steerMsg = GameMessage.SteerMsg.newBuilder()
                .setDirection(direction)
                .build();
        this.steerMsg = GameMessage.newBuilder()
                .setMsgSeq(msg_seq)
                .setSteer(steerMsg)
                .setSenderId(my_id)
                .build();
    }

    public byte[] getBytes() {
        return steerMsg.toByteArray();
    }
}
