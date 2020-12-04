package game.multi.proto.viewers;

import dto.GamePlayer;
import dto.GameState;
import dto.NodeRole;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class GamePlayersViewer {
    private final String SPACE = " ";
    private final GameState gameState;

    public GamePlayersViewer(GameState gameState) {
        this.gameState = gameState;
    }

    public List<InetSocketAddress> getSocketAddressOfAllPlayersWithOutMaster() {
        List<InetSocketAddress> ips = new ArrayList<>();
        List<GamePlayer> players = gameState.getPlayers().getPlayersList();
        for (GamePlayer currentPlayer : players) {
            ips.add(new InetSocketAddress(currentPlayer.getIpAddress(), currentPlayer.getPort()));
        }
        return ips;
    }

    public InetSocketAddress getMasterAddress() {
        return new InetSocketAddress(getMasterPlayer().getIpAddress(), getMasterPlayer().getPort());
    }

    public GamePlayer getMasterPlayer() {
        List<GamePlayer> gamePlayerList = gameState.getPlayers().getPlayersList();
        for (GamePlayer currentPlayer : gamePlayerList) {
            if (currentPlayer.getRole() == NodeRole.MASTER) {
                return currentPlayer;
            }
        }
        return null;
    }

    public List<String> getPlayersListToDraw() {
        List<String> answer = new ArrayList<>();
        List<GamePlayer> gamePlayerList = gameState.getPlayers().getPlayersList();
        for (GamePlayer currentGamePlayer : gamePlayerList) {
            answer.add(
                    currentGamePlayer.getName() + SPACE +
                            currentGamePlayer.getId() + SPACE +
                            currentGamePlayer.getIpAddress() + SPACE +
                            currentGamePlayer.getPort() + SPACE +
                            currentGamePlayer.getRole() + SPACE +
                            currentGamePlayer.getScore()
            );
        }
        return answer;
    }
}
