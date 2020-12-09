package game.multi.proto.renovators;

import dto.*;
import game.multi.GamePlay;

public class GamePlayersRenovator {
    private GamePlay gamePlay;

    public GamePlayersRenovator(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    public void addPlayer(String name
            , int id
            , String ip
            , int port
            , NodeRole nodeRole
            , PlayerType playerType
            , int score) {
        GamePlayer gamePlayer = GamePlayer.newBuilder()
                .setScore(score)
                .setType(playerType)
                .setRole(nodeRole)
                .setPort(port)
                .setIpAddress(ip)
                .setId(id)
                .setName(name)
                .build();

        GamePlayers gamePlayers = GamePlayers.newBuilder(gamePlay.getGameState().getPlayers())
                .addPlayers(gamePlayer)
                .build();
        GameState gameState = GameState.newBuilder(gamePlay.getGameState())
                .setPlayers(gamePlayers)
                .build();
        gamePlay.updateGameState(gameState);
    }

    public void addViewer(int player_id, String hostAddress, int port, String name) {
        GamePlayer gamePlayer = GamePlayer.newBuilder()
                .setRole(NodeRole.VIEWER)
                .setName(name)
                .setScore(0)
                .setPort(port)
                .setIpAddress(hostAddress)
                .setId(player_id)
                .build();

        GamePlayers gamePlayers = GamePlayers.newBuilder(gamePlay.getGameState().getPlayers())
                .addPlayers(gamePlayer)
                .build();
        GameState gameState = GameState.newBuilder(gamePlay.getGameState())
                .setPlayers(gamePlayers)
                .build();
        gamePlay.updateGameState(gameState);
    }
}
