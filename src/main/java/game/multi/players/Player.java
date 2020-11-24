package game.multi.players;

import dto.NodeRole;
import game.multi.Game;

public interface Player {
    NodeRole play(Game game);
}
