package game.multi.proto.creators;

import dto.GameMessage;
import dto.GameState;

public class AnnouncmentMessageCreator {
    private final GameMessage.AnnouncementMsg announcementMsg;

    public AnnouncmentMessageCreator(Integer msg_seq, GameState gameState) {
        this.announcementMsg = GameMessage.AnnouncementMsg.newBuilder()
                .
                .setPlayers(gameState.getPlayers())
                .setConfig(gameState.getConfig())
                .build();
    }

    public byte[] getBytes() {
        return announcementMsg.toByteArray();
    }
}
