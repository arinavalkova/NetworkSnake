package game.multi.proto.creators;

import dto.GameMessage;
import dto.GameState;

public class AnnouncmentMessageCreator {
    private final GameMessage announcementMsg;

    public AnnouncmentMessageCreator(Integer msg_seq, GameState gameState) {
        GameMessage.AnnouncementMsg announcementMsg = GameMessage.AnnouncementMsg.newBuilder()
                .setPlayers(gameState.getPlayers())
                .setConfig(gameState.getConfig())
                .build();
        this.announcementMsg = GameMessage.newBuilder()
                .setMsgSeq(msg_seq)
                .setAnnouncement(announcementMsg)
                .build();
    }

    public byte[] getBytes() {
        return announcementMsg.toByteArray();
    }
}
