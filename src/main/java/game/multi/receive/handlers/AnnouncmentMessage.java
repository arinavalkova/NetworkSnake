package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.receive.ReceiverFactory;

public class AnnouncmentMessage implements MessageHandler{
    @Override
    public void handle(ReceiverFactory receiverFactory, GameMessage currentMessage) {
        //parse GameMessage here
        receiverFactory.getCurrentGames().update();
    }
}
