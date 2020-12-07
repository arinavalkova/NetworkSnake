package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.GamePlay;
import game.multi.receive.ReceiverMulticast;

import java.net.InetSocketAddress;

public class AckMessageHandler implements MessageHandler {
    @Override
    public void handle(InetSocketAddress socketAddress, GamePlay gamePlay, GameMessage currentMessage) {
        //parse GameMessage here
        int mess_seq = 1; /* TEST */
        //receiverFactory.getGame().getConfirmSender().confirmMessage(mess_seq);
    }
}