package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.receive.ReceiverFactory;

import java.net.SocketAddress;

public class RoleChangeMessageHandler implements MessageHandler {
    @Override
    public void handle(SocketAddress socketAddress, ReceiverFactory receiverFactory, GameMessage currentMessage) {
        //тоже добавить в сразу не обрабатываемые
        //для олученного id сказать что змейка ZOMBIE
    }
}
