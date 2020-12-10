package game.multi.proto.renovators;

import dto.GamePlayer;
import dto.GamePlayers;
import dto.GameState;
import dto.NodeRole;
import game.multi.GamePlay;
import game.multi.proto.viewers.GamePlayerViewer;
import org.w3c.dom.Node;

public class GamePlayerRenovator {
    private final static String SPACE = " ";

    private GamePlay gamePlay;

    public GamePlayerRenovator(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    public void updateIpAddress(int playerId, String ipAddress) {
        GameState gameState = gamePlay.getGameState();
        int playerNum = new GamePlayerViewer(gameState).findPlayerById(playerId);
        GamePlayer gamePlayer = GamePlayer.newBuilder(gameState.getPlayers().getPlayers(playerNum))
                .setIpAddress(ipAddress)
                .build();
        GamePlayers gamePlayers = GamePlayers.newBuilder(gameState.getPlayers())
                .setPlayers(playerNum, gamePlayer)
                .build();
        gameState = GameState.newBuilder(gameState)
                .setPlayers(gamePlayers)
                .build();
        gamePlay.updateGameState(gameState);
    }

    public void updateNodeRole(int playerId, NodeRole nodeRole) {
        GameState gameState = gamePlay.getGameState();
        int playerNum = new GamePlayerViewer(gameState).findPlayerById(playerId);
        GamePlayer gamePlayer = GamePlayer.newBuilder(gameState.getPlayers().getPlayers(playerNum))
                .setRole(nodeRole)
                .build();
        GamePlayers gamePlayers = GamePlayers.newBuilder(gameState.getPlayers())
                .setPlayers(playerNum, gamePlayer)
                .build();
        gameState = GameState.newBuilder(gameState)
                .setPlayers(gamePlayers)
                .build();
        gamePlay.updateGameState(gameState);
    }

    public void incPoints(int playerId) {
        GameState gameState = gamePlay.getGameState();
        int playerNum = new GamePlayerViewer(gameState).findPlayerById(playerId);
        GamePlayer gamePlayer = GamePlayer.newBuilder(gameState.getPlayers().getPlayers(playerNum))
                .setScore(gameState
                        .getPlayers()
                        .getPlayers(playerNum)
                        .getScore() + 1
                )
                .build();
        GamePlayers gamePlayers = GamePlayers.newBuilder(gameState.getPlayers())
                .setPlayers(playerNum, gamePlayer)
                .build();
        gameState = GameState.newBuilder(gameState)
                .setPlayers(gamePlayers)
                .build();
        gamePlay.updateGameState(gameState);
    }

    public void playerBecomeViewer(int playerId) {
        GameState gameState = gamePlay.getGameState();
        int playerNum = new GamePlayerViewer(gameState).findPlayerById(playerId);
        GamePlayer gamePlayer = GamePlayer.newBuilder(gameState.getPlayers().getPlayers(playerNum))
                .setScore(0)
                .setRole(NodeRole.VIEWER)
                .build();
        GamePlayers gamePlayers = GamePlayers.newBuilder(gameState.getPlayers())
                .setPlayers(playerNum, gamePlayer)
                .build();
        gameState = GameState.newBuilder(gameState)
                .setPlayers(gamePlayers)
                .build();
        gamePlay.updateGameState(gameState);
    }
}
