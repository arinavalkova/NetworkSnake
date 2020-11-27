package game.multi.receive.handlers;

import game.multi.Game;
import game.multi.receive.ReceiverFactory;

public interface MessageHandler {
    void handle(ReceiverFactory receiverFactory /* ,MESSAGE message*/);
}
