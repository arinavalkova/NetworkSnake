package game.multi.proto.renovators;

import dto.Direction;
import dto.GameState;
import game.multi.GamePlay;
import game.multi.proto.viewers.SnakeViewer;

import java.util.ArrayList;
import java.util.List;

public class SnakeRenovator {
    private static final float MIN_DEAD_FOOD_PROB = 0;
    private static final float MAX_DEAD_FOOD_PROB = 1;
    private GamePlay gamePlay;

    public SnakeRenovator(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    public GameState.Coord snakeMove(int playerId) {
        GameState gameState = gamePlay.getGameState();
        int snakeId = new SnakeViewer(gameState).getSnakeIdByPlayerId(playerId);
        GameState.Coord headCoord = gameState.getSnakes(snakeId).getPoints(0);
        Direction snakeDirection = gameState.getSnakes(snakeId).getHeadDirection();
        GameState.Coord newHeadCoord = new SnakeViewer(gameState).moveCoord(headCoord, snakeDirection, gameState);
        List<GameState.Coord> oldSnakeCoords = gameState.getSnakes(snakeId).getPointsList();
        List<GameState.Coord> newSnakeCoords = new ArrayList<>();
        newSnakeCoords.add(newHeadCoord);
        newSnakeCoords.addAll(oldSnakeCoords);
        newSnakeCoords.remove(newSnakeCoords.size() - 1);

        gameState = GameState.newBuilder(gameState)
                .setSnakes(snakeId, gameState
                        .getSnakes(snakeId)
                        .toBuilder()
                        .clearPoints()
                        .addAllPoints(newSnakeCoords)
                        .build()
                ).build();
        gamePlay.updateGameState(gameState);
        return newHeadCoord;
    }

    public GameState.Coord snakeEat(int playerId) {
        GameState gameState = gamePlay.getGameState();
        int snakeId = new SnakeViewer(gameState).getSnakeIdByPlayerId(playerId);
        GameState.Coord headCoord = gameState.getSnakes(snakeId).getPoints(0);
        Direction snakeDirection = gameState.getSnakes(snakeId).getHeadDirection();
        GameState.Coord newHeadCoord = new SnakeViewer(gameState).moveCoord(headCoord, snakeDirection, gameState);
        List<GameState.Coord> oldSnakeCoords = gameState.getSnakes(snakeId).getPointsList();
        List<GameState.Coord> newSnakeCoords = new ArrayList<>();
        newSnakeCoords.add(newHeadCoord);
        newSnakeCoords.addAll(oldSnakeCoords);

        gameState = GameState.newBuilder(gameState)
                .setSnakes(snakeId, gameState
                        .getSnakes(snakeId)
                        .toBuilder()
                        .clearPoints()
                        .addAllPoints(newSnakeCoords)
                        .build()
                ).build();
        gamePlay.updateGameState(gameState);
        return newHeadCoord;
    }

    public void updateSnakeDirectionByPlayerId(int playerId, Direction direction) {
        GameState gameState = gamePlay.getGameState();
        int snakeId = new SnakeViewer(gameState).getSnakeIdByPlayerId(playerId);
        gameState = GameState.newBuilder(gameState)
                .setSnakes(snakeId,
                        gameState
                                .getSnakes(snakeId)
                                .toBuilder()
                                .setHeadDirection(direction)
                                .build())
                .build();
        gamePlay.updateGameState(gameState);
    }

    public void updateSnakeStateByPlayerId(int playerId, GameState.Snake.SnakeState snakeState) {
        GameState gameState = gamePlay.getGameState();
        int snakeId = new SnakeViewer(gameState).getSnakeIdByPlayerId(playerId);
        gameState = GameState.newBuilder(gameState)
                .setSnakes(snakeId,
                        gameState
                                .getSnakes(snakeId)
                                .toBuilder()
                                .setState(snakeState)
                                .build())
                .build();
        gamePlay.updateGameState(gameState);
    }

    public void deleteSnake(int playerId) {
        GameState gameState = gamePlay.getGameState();
        int snakeId = new SnakeViewer(gameState).getSnakeIdByPlayerId(playerId);
        generateFoodFromSnake(gameState.getSnakes(snakeId).getPointsList());
        gameState = GameState.newBuilder(gamePlay.getGameState())
                .removeSnakes(snakeId)
                .build();
        gamePlay.updateGameState(gameState);
    }

    private void generateFoodFromSnake(List<GameState.Coord> pointsList) {
        main.Random random = new main.Random();
        float chanceOfTurningIntoFood = gamePlay.getGameState().getConfig().getDeadFoodProb();
        GameStateRenovator gameStateRenovator = new GameStateRenovator(gamePlay);
        for (GameState.Coord currentCoord : pointsList) {
            float randomNumber = random.inBoundsFloat(MIN_DEAD_FOOD_PROB, MAX_DEAD_FOOD_PROB);
            if (randomNumber <= chanceOfTurningIntoFood) {
                gameStateRenovator.addFood(currentCoord);
            }
        }
        gamePlay.updateGameState(gameStateRenovator.getGameState());
    }

    public GameState.Snake getMySnake() {
        int my_id = gamePlay.getMy_id();
        Integer snake_id = new SnakeViewer(gamePlay.getGameState()).getSnakeIdByPlayerId(my_id);
        GameState.Snake snake = null;
        try {
            snake = gamePlay
                    .getGameState()
                    .getSnakes(snake_id);
        } catch (NullPointerException nullPointerException) {
            return null;
        }
        if (snake.getState() == GameState.Snake.SnakeState.ZOMBIE) {
            return null;
        }
        return snake;
    }

    public void makeSnakeZombie(int playerId) {
        GameState gameState = gamePlay.getGameState();
        Integer snake_id = new SnakeViewer(gameState).getSnakeIdByPlayerId(playerId);
        if (snake_id == null) {
            return;
        }
        gameState = GameState.newBuilder(gameState)
                .setSnakes(snake_id,
                        gameState
                                .getSnakes(snake_id)
                                .toBuilder()
                                .setState(GameState.Snake.SnakeState.ZOMBIE)
                                .build())
                .build();
        gamePlay.updateGameState(gameState);
    }

    public GameState getGameState() {
        return gamePlay.getGameState();
    }
}

