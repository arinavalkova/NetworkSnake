package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.receive.ReceiverFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class AckMessageHandler implements MessageHandler {
    @Override
    public void handle(SocketAddress socketAddress, ReceiverFactory receiverFactory, GameMessage currentMessage) {
        //parse GameMessage here
        int mess_seq = 1; /* TEST */
        //receiverFactory.getGame().getConfirmSender().confirmMessage(mess_seq);
    }
}