package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.proto.renovators.SnakeRenovator;
import game.multi.receive.ReceiverFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class SteerMessageHandler implements MessageHandler {
    @Override
    public void handle(InetSocketAddress socketAddress, ReceiverFactory receiverFactory, GameMessage currentMessage) {
        System.out.println(
                currentMessage.getSenderId() + " " +
                currentMessage.getSteer().getDirection()
        );
        SnakeRenovator snakeRenovator = new SnakeRenovator(receiverFactory.getGame());
        snakeRenovator.updateSnakeDirectionByPlayerId(
                currentMessage.getSenderId(),
                currentMessage.getSteer().getDirection()
        );
    }
}