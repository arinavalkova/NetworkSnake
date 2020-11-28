package game.multi.proto.parsers;

import com.google.protobuf.InvalidProtocolBufferException;
import dto.GameMessage;
import game.multi.receive.Messages;

public class ProtoParser {
    private final GameMessage gameMessage;

    public ProtoParser(byte[] message) throws InvalidProtocolBufferException {
        this.gameMessage = GameMessage.parseFrom(message);
    }

    public Messages getType() {
        return null;                  // <---- TO DO
    }

    public GameMessage getGameMessage() {
        return gameMessage;
    }
}
