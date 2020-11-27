package game.multi.receive.handlers;

import game.multi.Game;
import game.multi.receive.ReceiverFactory;

public class AnnouncmentMessage implements MessageHandler{
    @Override
    public void handle(ReceiverFactory receiverFactory) {
        receiverFactory.getCurrentGames().update();
    }
}
