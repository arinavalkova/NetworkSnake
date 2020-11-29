package game.multi.players;

import dto.NodeRole;
import game.multi.Game;
import game.multi.Server;
import game.multi.field.CellRole;

public class MasterPlayer implements Player {
    @Override
    public void play(Game game) {
        if (game.getGameWindowController().getButtonNodeRole() == NodeRole.VIEWER) {
            //send deputy that he is master now
            //send deputy also all raw messages
            //send deputy that my snake is ZOMBIE
            //stop all master sendings
            game.setNodeRole(NodeRole.VIEWER);
            return;
        }
        //Server.getSenderMulticast().updateGameStateInvite("Hello");     /* Invite send thread updating */
        int points;//play check all which uplyed by receiver
        if ((points = game.getSnakeMover().start()) == -1) {
            Server.getSenderMulticast().stop();
            //send deputy that he is master now
            //send deputy also all raw messages
            //send deputy that my snake is ZOMBIE
            //stop all master sendings
            game.setGameOver(true);
        }
        //send to all players GameState
    }
}