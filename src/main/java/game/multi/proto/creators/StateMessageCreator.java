package game.multi.proto.creators;

import dto.GameMessage;
import dto.GameState;

public class StateMessageCreator {
    private final GameMessage stateMessage;

    public StateMessageCreator(Integer msg_seq, GameState gameState) {
        GameMessage.StateMsg stateMsg = GameMessage.StateMsg.newBuilder()
                .setState(gameState)
                .build();
        this.stateMessage = GameMessage.newBuilder()
                .setMsgSeq(msg_seq)
                .setState(stateMsg)
                .build();
    }

    public byte[] getBytes() {
        return stateMessage.toByteArray();
    }
}
