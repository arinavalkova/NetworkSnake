package game.multi.proto.viewers;

import dto.*;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class GamePlayersViewer {
    private final String SPACE = " ";
    private GameState gameState;

    public GamePlayersViewer(GameState gameState) {
        this.gameState = gameState;
    }

    public List<InetSocketAddress> getSocketAddressOfAllPlayersWithOutMaster() {
        List<InetSocketAddress> ips = new ArrayList<>();
        List<GamePlayer> players = gameState.getPlayers().getPlayersList();
        for (GamePlayer currentPlayer : players) {
             if( currentPlayer.getRole() != NodeRole.MASTER) {
                 ips.add(new InetSocketAddress(currentPlayer.getIpAddress(), currentPlayer.getPort()));
             }
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

    public GameState getGameState() {
        return gameState;
    }

    public InetSocketAddress getDeputySocketAddress() {
        GamePlayer deputyPlayer = findDeputyPlayer();
        if (deputyPlayer == null) {
            return null;
        }
        return new InetSocketAddress(deputyPlayer.getIpAddress(),
                deputyPlayer.getPort());
    }

    public GamePlayer findDeputyPlayer() {
        List<GamePlayer> players = gameState.getPlayers().getPlayersList();
        for (GamePlayer currentGamePlayer : players) {
            if (currentGamePlayer.getRole() == NodeRole.DEPUTY) {
                return currentGamePlayer;
            }
        }
        return null;
    }

    public List<GamePlayer> getAllPlayers() {
        return gameState.getPlayers().getPlayersList();
    }
}
