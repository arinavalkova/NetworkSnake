package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.receive.ReceiverFactory;

import java.net.SocketAddress;

public class SteerMessageHandler implements MessageHandler {
    @Override
    public void handle(SocketAddress socketAddress, ReceiverFactory receiverFactory, GameMessage currentMessage) {
        
    }
}