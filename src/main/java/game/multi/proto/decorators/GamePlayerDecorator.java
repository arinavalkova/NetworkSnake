package game.multi.proto.decorators;

import dto.GamePlayer;
import dto.NodeRole;
import dto.PlayerType;

public class GamePlayerDecorator {
    private final static String SPACE = " ";

    private GamePlayer gamePlayer;

    public GamePlayerDecorator(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public String getName() {
        return gamePlayer.getName();
    }

    public int getId() {
        return gamePlayer.getId();
    }

    public String getIpAddress() {
        return gamePlayer.getIpAddress();
    }

    public int getPort() {
        return gamePlayer.getPort();
    }

    public NodeRole getNodeRole() {
        return gamePlayer.getRole();
    }

    public PlayerType getPlayerType() {
        return gamePlayer.getType();
    }

    public int getScore() {
        return gamePlayer.getScore();
    }

    public void updateIpAddress(String ipAddress) {
        gamePlayer = GamePlayer
                .newBuilder(gamePlayer)
                .setIpAddress(ipAddress)
                .build();
    }

    public void updateNodeRole(NodeRole nodeRole) {
        gamePlayer = GamePlayer
                .newBuilder(gamePlayer)
                .setRole(nodeRole)
                .build();
    }

    public void incPoints() {
        gamePlayer = GamePlayer
                .newBuilder(gamePlayer)
                .setScore(gamePlayer.getScore() + 1)
                .build();
    }

    @Override
    public String toString() {
        return gamePlayer.getName() + SPACE +
                gamePlayer.getId() + SPACE +
                gamePlayer.getIpAddress() + SPACE +
                gamePlayer.getPort() + SPACE +
                gamePlayer.getRole() + SPACE +
                gamePlayer.getScore();
    }
}
