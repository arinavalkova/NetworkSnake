package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.GamePlay;
import game.multi.receive.ReceiverMulticast;

import java.net.InetSocketAddress;

public class RoleChangeMessageHandler implements MessageHandler {
    @Override
    public void handle(InetSocketAddress socketAddress, GamePlay gamePlay, GameMessage currentMessage) {
        //тоже добавить в сразу не обрабатываемые
        //для олученного id сказать что змейка ZOMBIE
    }
}
