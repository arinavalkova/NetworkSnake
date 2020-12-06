package game.multi.receive;

import dto.*;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CurrentGames {
    private final static int GAME_ANNOUNCE_TIME_OUT = 2000;
    private final static String PLAYERS = "Players: ";
    private final static String STATE_DELAY = "State delay: ";
    private final static String PLUS = "+";
    private final static String X = "X";
    private final static String SPACE = " ";
    private final static String NO_GAMES_FOR_JOIN = "No games for join";
    private final Map<String, InetSocketAddress> currentGamesMap;
    private final Map<SocketAddress, Long> currentTimeMap;

    public CurrentGames() {
        currentGamesMap = new ConcurrentHashMap<>();
        currentTimeMap = new ConcurrentHashMap<>();
    }

    public void update(InetSocketAddress socketAddress, GameMessage gameMessage) {
        synchronized (currentGamesMap) {
            String currentGame = findStringBySocketAddress(socketAddress);
            if (currentGame != null) {
                currentGamesMap.remove(currentGame);
            }
            currentTimeMap.put(socketAddress, System.currentTimeMillis());
            currentGamesMap.put(parseGameMessage(gameMessage), socketAddress);
        }
    }

    public String findStringBySocketAddress(SocketAddress socketAddress) {
        synchronized (currentGamesMap) {
            for (Map.Entry<String, InetSocketAddress> entry : currentGamesMap.entrySet()) {
                if (entry.getValue().equals(socketAddress))
                    return entry.getKey();
            }
        }
        return null;
    }

    public ArrayList<String> getCurrentGames() {
        ArrayList<String> currentGames = new ArrayList<>();
        synchronized (currentGamesMap) {
            if (currentGamesMap.isEmpty()) {
                currentGames.add(NO_GAMES_FOR_JOIN);
            } else {
                for (Map.Entry<String, InetSocketAddress> entry : currentGamesMap.entrySet()) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - currentTimeMap.get(entry.getValue()) > GAME_ANNOUNCE_TIME_OUT) {
                        currentTimeMap.remove(entry.getValue());
                        currentGamesMap.remove(entry.getKey());
                    } else {
                        currentGames.add(entry.getKey());
                    }
                }
            }
        }
        return currentGames;
    }

    private String parseGameMessage(GameMessage gameMessage) {
        GameMessage.AnnouncementMsg announcementMsg = gameMessage.getAnnouncement();
        return parseGame(announcementMsg.getConfig(), announcementMsg.getPlayers());
    }

    private String parseGame(GameConfig gameConfig, GamePlayers gamePlayers) {
        String parsedMessage = gameConfig.getWidth() +
                X +
                gameConfig.getHeight() +
                SPACE +
                gameConfig.getFoodStatic() +
                PLUS +
                gameConfig.getFoodPerPlayer() +
                X +
                SPACE +
                STATE_DELAY +
                gameConfig.getStateDelayMs() +
                SPACE +
                PLAYERS;
        List<GamePlayer> players = gamePlayers.getPlayersList();
        for (GamePlayer currentPlayer : players) {
            parsedMessage += currentPlayer.getName() + SPACE;
        }
        return parsedMessage;
    }

    public void deleteGame(GameState gameState) {
        synchronized (currentGamesMap) {
            currentGamesMap.remove(parseGame(gameState.getConfig(), gameState.getPlayers()));
        }
    }

    public InetSocketAddress findAddressByStringLine(String currentGameLine) {
        return currentGamesMap.get(currentGameLine);
    }
}
