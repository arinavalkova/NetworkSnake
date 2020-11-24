package game.multi.players;

import dto.NodeRole;
import game.multi.Game;
import game.multi.field.CellRole;

public class MasterPlayer implements Player {
    @Override
    public NodeRole play(Game game) {
        NodeRole nodeRole = game.getNodeRole();
        game.getSenderMulticast().updateGameStateInvite("Hello");     /* TEST */
        int points;
        if ((points = game.getSnakeMover().start()) == -1) {
            //stop sending invites
        }
        receiveMessageProcessing(game);
        //update my role
        //send to all players GameState
        game.getGameWindowController().setPoints(points);
        game.getGameFieldDrawer().redrawField();
        game.getGameFieldDrawer().draw(CellRole.FOOD);
        game.getGameFieldDrawer().draw(CellRole.SNAKE);
        return nodeRole;
    }

    private void receiveMessageProcessing(Game game) {
        while (game.getReceiverUnicast().takeReceivedMessage() != null) {
            //обработка сообщений по типу
        }
    }
}
