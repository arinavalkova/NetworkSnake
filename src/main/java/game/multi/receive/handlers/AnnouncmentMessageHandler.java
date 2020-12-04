package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.receive.ReceiverFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;

public class AnnouncmentMessageHandler implements MessageHandler {
    @Override
    public void handle(SocketAddress socketAddress, ReceiverFactory receiverFactory, GameMessage currentMessage) {
        receiverFactory.getCurrentGames().update(socketAddress, currentMessage);
    }
}
