package game.multi.proto.creators;

import dto.GameMessage;
import dto.PlayerType;

public class JoinMessageCreator {
    private final GameMessage joinMessage;

    public JoinMessageCreator(Integer msg_seq, boolean only_view, String name, Integer sender_id) {
        GameMessage.JoinMsg joinMsg = GameMessage.JoinMsg.newBuilder()
                .setPlayerType(PlayerType.HUMAN)
                .setOnlyView(only_view)
                .setName(name)
                .build();
        this.joinMessage = GameMessage.newBuilder()
                .setMsgSeq(msg_seq)
                .setJoin(joinMsg)
                .setSenderId(sender_id)
                .build();
    }

    public byte[] getBytes() {
        return joinMessage.toByteArray();
    }
}
