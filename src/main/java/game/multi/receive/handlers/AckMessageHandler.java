package game.multi.receive.handlers;

import dto.GameMessage;
import dto.NodeRole;
import game.multi.GamePlay;
import game.multi.receive.ReceiverMulticast;

import java.net.InetSocketAddress;

public class AckMessageHandler implements MessageHandler {
    @Override
    public void handle(InetSocketAddress socketAddress, GamePlay gamePlay, GameMessage currentMessage) {
        gamePlay.setMyId(currentMessage.getReceiverId());
        gamePlay.getConfirmSender().confirmMessage(currentMessage.getMsgSeq());
    }
}