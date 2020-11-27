package game.multi.receive.handlers;

import game.multi.receive.ReceiverFactory;

public class AckMessage implements MessageHandler{
    @Override
    public void handle(ReceiverFactory receiverFactory) {
        // Подтверждение сообщения с таким же seq
        // Обновление времени получения сообщения в ConfirmSender
        int mess_seq = 1; /* TEST */
        receiverFactory.getGame().getConfirmSender().confirmMessage(mess_seq);
    }
}
