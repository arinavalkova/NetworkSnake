package game.multi.proto.creators;

import dto.GameMessage;

public class ErrorMessageCreator {
    private final Integer msg_seq;
    private final Integer sender_id;
    private final Integer receiver_id;
    private final String errorMessage;

    public ErrorMessageCreator(Integer msg_seq, Integer sender_id, Integer receiver_id, String errorMessage) {
        this.msg_seq = msg_seq;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.errorMessage = errorMessage;
    }

    public byte[] getBytes() {
        GameMessage.ErrorMsg.Builder errorMessageBuilder = GameMessage.ErrorMsg.newBuilder();
        errorMessageBuilder.setErrorMessage(errorMessage);

        GameMessage.Builder gameMessageBuilder = GameMessage.newBuilder();
        gameMessageBuilder.setMsgSeq(msg_seq);
        if (sender_id != null) {
            gameMessageBuilder.setSenderId(sender_id);
        }
        if (receiver_id != null) {
            gameMessageBuilder.setReceiverId(receiver_id);
        }
        gameMessageBuilder.setError(errorMessageBuilder.build());

        return gameMessageBuilder.build().toByteArray();
    }
}
