package game.multi.proto.creators;

import dto.GameMessage;

public class ErrorMessageCreator {
    private final GameMessage errorMessage;

    public ErrorMessageCreator(Integer msg_seq, String errorMessage) {
        GameMessage.ErrorMsg errorMsg = GameMessage.ErrorMsg.newBuilder()
                .setErrorMessage(errorMessage)
                .build();
        this.errorMessage = GameMessage.newBuilder()
                .setMsgSeq(msg_seq)
                .setError(errorMsg)
                .build();
    }

    public byte[] getBytes() {
        return errorMessage.toByteArray();
    }
}
