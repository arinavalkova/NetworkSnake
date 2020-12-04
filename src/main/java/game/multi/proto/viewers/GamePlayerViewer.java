package game.multi.proto.viewers;

import dto.GamePlayer;
import dto.GameState;

import java.util.List;

public class GamePlayerViewer {
    private final GameState gameState;

    public GamePlayerViewer(GameState gameState) {
        this.gameState = gameState;
    }

    public Integer findPlayerById(int playerId) {
        List<GamePlayer> gamePlayerList = gameState.getPlayers().getPlayersList();
        for (int i = 0; i < gamePlayerList.size(); i++) {
            if (gamePlayerList.get(i).getId() == playerId) {
                return i;
            }
        }
        return null;
    }
}
