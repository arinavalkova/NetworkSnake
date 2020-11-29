package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.receive.ReceiverFactory;

public class RoleChangeMessageHandler implements MessageHandler {
    @Override
    public void handle(ReceiverFactory receiverFactory, GameMessage currentMessage) {
        //тоже добавить в сразу не обрабатываемые
        //для олученного id сказать что змейка ZOMBIE
    }
}
