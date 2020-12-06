package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.receive.ReceiverFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class StateMessageHandler implements MessageHandler {
    @Override
    public void handle(InetSocketAddress socketAddress, ReceiverFactory receiverFactory, GameMessage currentMessage) {
        receiverFactory.getGame().updateGameState(currentMessage.getState().getState());
    }
}
