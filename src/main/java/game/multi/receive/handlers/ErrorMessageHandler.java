package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.receive.ReceiverFactory;

public class ErrorMessageHandler implements MessageHandler {
    @Override
    public void handle(ReceiverFactory receiverFactory, GameMessage currentMessage) {
        receiverFactory.getWaitingForProcessingMessages().add(currentMessage);
    }
}
