package game.multi.players;

import game.multi.Game;
import game.multi.field.CellRole;

public class MasterPlayer implements Player {
    @Override
    public int play(Game game) {
        game.getSenderMulticast().updateGameStateInvite("Hello");     /* TEST */
        int points;
        if ((points = game.getSnakeMover().start()) == -1) {
            game.getSenderMulticast().stop();
            //send to deputy last receivedMessages
            return -1;
        }
        receiveMessageProcessing(game);
        //send to all players GameState
        game.getGameWindowController().setPoints(points);
        game.getGameFieldDrawer().redrawField();
        game.getGameFieldDrawer().draw(CellRole.FOOD);
        game.getGameFieldDrawer().draw(CellRole.SNAKE);
        return 0;
    }

    private void receiveMessageProcessing(Game game) {
        while (game.getReceiverUnicast().takeReceivedMessage() != null) {
            //обработка сообщений по типу
        }
    }
}
