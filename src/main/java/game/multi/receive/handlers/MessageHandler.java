package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.receive.ReceiverFactory;

public interface MessageHandler {
    void handle(ReceiverFactory receiverFactory, GameMessage currentMessage);
}
