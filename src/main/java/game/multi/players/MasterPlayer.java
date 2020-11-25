package game.multi.players;

import dto.NodeRole;
import game.multi.Game;
import game.multi.field.CellRole;

public class MasterPlayer implements Player {
    @Override
    public int play(Game game) {
        if (game.getGameWindowController().getButtonNodeRole() == NodeRole.VIEWER) {
            //send deputy that he is master now
            //send deputy also all raw messages
            //send deputy that my snake is ZOMBIE
            //stop all master sendings
            game.setNodeRole(NodeRole.VIEWER);
            return 0;
        }
        game.getSenderMulticast().updateGameStateInvite("Hello");     /* Invite send thread updating */
        int points;//play check all which uplyed by receiver
        if ((points = game.getSnakeMover().start()) == -1) {
            game.getSenderMulticast().stop();
            //send deputy that he is master now
            //send deputy also all raw messages
            //send deputy that my snake is ZOMBIE
            //stop all master sendings
            return -1;
        }
        //receiveMessageProcessing(game); --> maybe unless
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
