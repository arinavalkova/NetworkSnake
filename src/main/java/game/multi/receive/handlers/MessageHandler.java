package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.GamePlay;
import game.multi.receive.ReceiverMulticast;

import java.net.InetSocketAddress;

public interface MessageHandler {
    void handle(InetSocketAddress socketAddress, GamePlay gamePlay, GameMessage currentMessage);
}
