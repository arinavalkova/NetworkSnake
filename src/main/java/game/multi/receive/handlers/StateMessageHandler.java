package game.multi.receive.handlers;

import dto.GameMessage;
import game.multi.GamePlay;
import game.multi.receive.ReceiverMulticast;

import java.net.InetSocketAddress;

public class StateMessageHandler implements MessageHandler {
    @Override
    public void handle(InetSocketAddress socketAddress, GamePlay gamePlay, GameMessage currentMessage) {
        gamePlay.getGameFieldDrawer().redrawField(gamePlay);
        gamePlay.updateGameState(currentMessage.getState().getState());
        gamePlay.getGameFieldDrawer().redrawField(gamePlay);
        System.out.println(gamePlay.getMyNodeRole());
    }
}
