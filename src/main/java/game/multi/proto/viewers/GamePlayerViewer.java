package game.multi.proto.viewers;

import dto.*;

import java.util.List;

public class GamePlayerViewer {
    private GameState gameState;

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

    public boolean isPlayerMaster(int playerId) {
        int player = findPlayerById(playerId);
        return gameState.getPlayers().getPlayers(player).getRole() == NodeRole.MASTER;
    }

    public String getPlayerName(int playerId) {
        int player = findPlayerById(playerId);
        return gameState.getPlayers().getPlayers(player).getName();
    }

    public NodeRole getNodeRoleById(int my_id) {
        int my_player = findPlayerById(my_id);
        return gameState.getPlayers().getPlayers(my_id).getRole();
    }
}