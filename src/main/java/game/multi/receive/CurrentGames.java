package game.multi.receive;

import dto.GameMessage;
import dto.GamePlayer;
import dto.GameState;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CurrentGames {
    private final static String PLAYERS = "Players: ";
    private final static String STATE_DELAY = "State delay: ";
    private final static String PLUS = "+";
    private final static String X = "X";
    private final static String SPACE = " ";
    private final static String NO_GAMES_FOR_JOIN = "No games for join";
    private final Map<SocketAddress, GameMessage> currentGamesMap;

    public CurrentGames() {
        currentGamesMap = new ConcurrentHashMap<>();
    }

    public void update(SocketAddress socketAddress, GameMessage gameMessage) {
        synchronized (currentGamesMap) {
            currentGamesMap.remove(socketAddress);
            currentGamesMap.put(socketAddress, gameMessage);
        }
    }

    public ArrayList<String> getCurrentGames() {
        ArrayList<String> currentGames = new ArrayList<>();
        synchronized (currentGamesMap) {
            if (currentGamesMap.isEmpty()) {
                currentGames.add(NO_GAMES_FOR_JOIN);
            } else {
                for (Map.Entry<SocketAddress, GameMessage> entry : currentGamesMap.entrySet()) {
                    currentGames.add(parseGameMessage(entry.getValue()));
                }
            }
        }
        return currentGames;
    }

    private String parseGameMessage(GameMessage gameMessage) {
        GameState gameState = gameMessage.getState().getState();
        String parsedMessage = gameState.getConfig().getWidth() +
                X +
                gameState.getConfig().getHeight() +
                SPACE +
                gameState.getConfig().getFoodStatic() +
                PLUS +
                gameState.getConfig().getFoodPerPlayer() +
                X +
                SPACE +
                STATE_DELAY +
                gameState.getConfig().getStateDelayMs() +
                PLAYERS;
        List<GamePlayer> players = gameState.getPlayers().getPlayersList();
        for (GamePlayer currentPlayer : players) {
            parsedMessage += currentPlayer.getName() + SPACE;
        }
        return parsedMessage;
    }
}
