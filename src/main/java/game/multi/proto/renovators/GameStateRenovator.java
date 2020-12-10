package game.multi.proto.renovators;

import dto.Direction;
import dto.GameState;
import dto.NodeRole;
import dto.PlayerType;
import game.multi.GamePlay;
import game.multi.proto.viewers.GameStateViewer;
import main.Random;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class GameStateRenovator {
    private static final Integer UNKNOWN_SENDER_ID = -1;
    private GamePlay gamePlay;

    public GameStateRenovator(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    public void generateFoodIfNecessary() {
        GameState gameState = gamePlay.getGameState();
        int neededCount = (int) (gameState.getConfig().getFoodStatic() +
                gameState.getConfig().getFoodPerPlayer() *
                        new GameStateViewer(gameState).getAliveSnakesCount()) - gameState.getFoodsCount();
        if (neededCount == 0) {
            return;
        }
        Random random = new Random();
        List<GameState.Coord> emptyCoords = new GameStateViewer(gameState).getAllEmptyCoords();
        for (int i = 0; i < neededCount; i++) {
            GameState.Coord randomCoord = emptyCoords.get(random.inBoundsInt(0, emptyCoords.size() - 1));
            gameState = GameState.newBuilder(gameState)
                    .addFoods(randomCoord)
                    .build();
            emptyCoords.remove(randomCoord);
        }
        gamePlay.updateGameState(gameState);
    }

    public boolean addSnake(int playerId) {
        GameState gameState = gamePlay.getGameState();
        List<GameState.Coord> startCoords = new GameStateViewer(gameState).findCoordsForNewSnake();
        if (startCoords.isEmpty())
            return false;
        GameState.Snake newSnake = GameState.Snake.newBuilder()
                .setHeadDirection(Direction.UP)
                .setState(GameState.Snake.SnakeState.ALIVE)
                .setPlayerId(playerId)
                .addAllPoints(startCoords)
                .build();
        gameState = GameState.newBuilder(gameState)
                .addSnakes(newSnake)
                .build();
        gamePlay.updateGameState(gameState);
        return true;
    }

    public void deleteFood(GameState.Coord foodCoord) {
        GameState gameState = gamePlay.getGameState();
        List<GameState.Coord> oldCoords = GameState.newBuilder(gameState)
                .getFoodsList();
        List<GameState.Coord> newCoords = new ArrayList<>(oldCoords);
        for (GameState.Coord currentCoord : newCoords) {
            if (currentCoord.getX() == foodCoord.getX() && currentCoord.getY() == foodCoord.getY()) {
                newCoords.remove(currentCoord);
                break;
            }
        }
        gameState = GameState.newBuilder(gameState)
                .clearFoods()
                .addAllFoods(newCoords)
                .build();
        gamePlay.updateGameState(gameState);
    }

    public Integer tryAddNewPlayer(
            String name,
            InetSocketAddress socketAddress,
            NodeRole nodeRole,
            Integer senderId) {
        int playerId;
        if (senderId.equals(UNKNOWN_SENDER_ID)) {
            playerId = gamePlay.getAndIncIssuedId();//////////////it is tooo bad
            if (!addSnake(playerId)) {
                return null;
            }
            GamePlayersRenovator gamePlayersRenovator = new GamePlayersRenovator(gamePlay);
            gamePlayersRenovator.addPlayer(name
                    , playerId
                    , socketAddress.getAddress().getHostAddress()
                    , socketAddress.getPort()
                    , nodeRole
                    , PlayerType.HUMAN
                    , 0);
        } else {
            playerId = senderId;
            if (!addSnake(playerId)) {
                return null;
            }
            new GamePlayerRenovator(gamePlay).updateNodeRole(playerId, NodeRole.NORMAL);
        }
        return playerId;
    }

    public int addViewerPlayer(InetSocketAddress socketAddress, String name) {
        int player_id = gamePlay.getAndIncIssuedId();
        GamePlayersRenovator gamePlayersRenovator = new GamePlayersRenovator(gamePlay);
        gamePlayersRenovator.addViewer(player_id
                , socketAddress.getAddress().getHostAddress()
                , socketAddress.getPort()
                , name
        );
        return player_id;
    }

    public void addFood(GameState.Coord coord) {
        GameState gameState = GameState.newBuilder(gamePlay.getGameState())
                .addFoods(coord)
                .build();
        gamePlay.updateGameState(gameState);
    }

    public GameState getGameState() {
        return gamePlay.getGameState();
    }
}
