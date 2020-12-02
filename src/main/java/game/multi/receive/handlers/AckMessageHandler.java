package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.receive.ReceiverFactory;

public class AckMessageHandler implements MessageHandler {
    @Override
    public void handle(ReceiverFactory receiverFactory, GameMessage currentMessage) {
        //parse GameMessage here
        int mess_seq = 1; /* TEST */
        //receiverFactory.getGame().getConfirmSender().confirmMessage(mess_seq);
    }
}