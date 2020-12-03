package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.receive.ReceiverFactory;

import java.net.SocketAddress;

public interface MessageHandler {
    void handle(SocketAddress socketAddress, ReceiverFactory receiverFactory, GameMessage currentMessage);
}
