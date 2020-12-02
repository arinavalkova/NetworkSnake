package game.multi.proto.decorators;

import dto.Direction;
import dto.GameState;
import main.Random;

import java.util.ArrayList;
import java.util.List;

public class SnakeDecorator {
    private final static int NEIGHBOUR_COUNT = 2;
    private GameState gameState;

    public SnakeDecorator(GameState gameState) {
        this.gameState = gameState;
    }


    public GameState.Coord snakeMove(int playerId) {
        int snakeId = getSnakeIdByPlayerId(playerId);
        GameState.Coord headCoord = gameState.getSnakes(snakeId).getPoints(0);
        Direction snakeDirection = gameState.getSnakes(snakeId).getHeadDirection();
        GameState.Coord newHeadCoord = moveCoord(headCoord, snakeDirection);
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
        return newHeadCoord;
    }

    private GameState.Coord moveCoord(GameState.Coord headCoord, Direction snakeDirection) {
        if (snakeDirection == Direction.RIGHT) {
            return checkOutOfBoundary(headCoord.getX() + 1, headCoord.getY());
        } else if (snakeDirection == Direction.LEFT) {
            return checkOutOfBoundary(headCoord.getX() - 1, headCoord.getY());
        } else if (snakeDirection == Direction.UP) {
            return checkOutOfBoundary(headCoord.getX(), headCoord.getY() - 1);
        }
        return checkOutOfBoundary(headCoord.getX(), headCoord.getY() + 1);
    }

    private GameState.Coord checkOutOfBoundary(int x, int y) {
        int gameFieldWidth = gameState.getConfig().getWidth();
        int gameFieldHeight = gameState.getConfig().getHeight();
        if (x < 0) {
            return GameState.Coord.newBuilder()
                    .setX(gameFieldWidth - 1)
                    .setY(y)
                    .build();
        } else if (x == gameFieldWidth) {
            return GameState.Coord.newBuilder()
                    .setX(0)
                    .setY(y)
                    .build();
        } else if (y < 0) {
            return GameState.Coord.newBuilder()
                    .setX(x)
                    .setY(gameFieldHeight - 1)
                    .build();
        } else if (y == gameFieldHeight) {
            return GameState.Coord.newBuilder()
                    .setX(x)
                    .setY(0)
                    .build();
        }
        return GameState.Coord.newBuilder()
                .setX(x)
                .setY(y)
                .build();
    }

    public GameState.Coord snakeEat(int playerId) {
        int snakeId = getSnakeIdByPlayerId(playerId);
        GameState.Coord headCoord = gameState.getSnakes(snakeId).getPoints(0);
        Direction snakeDirection = gameState.getSnakes(snakeId).getHeadDirection();
        GameState.Coord newHeadCoord = moveCoord(headCoord, snakeDirection);
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
        return newHeadCoord;
    }

    public void updateSnakeDirectionByPlayerId(int playerId, Direction direction) {
        int snakeId = getSnakeIdByPlayerId(playerId);
        gameState = GameState.newBuilder(gameState)
                .setSnakes(snakeId,
                        gameState
                                .getSnakes(snakeId)
                                .toBuilder()
                                .setHeadDirection(direction)
                                .build())
                .build();
    }

    public GameState getGameState() {
        return gameState;
    }

    public Direction getSnakeDirectionByPlayerId(int playerId) {
        int snakeId = getSnakeIdByPlayerId(playerId);
        return gameState
                .getSnakes(snakeId)
                .getHeadDirection();
    }

    public void updateSnakeStateByPlayerId(int playerId, GameState.Snake.SnakeState snakeState) {
        int snakeId = getSnakeIdByPlayerId(playerId);
        gameState = GameState.newBuilder(gameState)
                .setSnakes(snakeId,
                        gameState
                                .getSnakes(snakeId)
                                .toBuilder()
                                .setState(snakeState)
                                .build())
                .build();
    }

    public GameState.Snake.SnakeState getSnakeStateByPlayerId(int playerId) {
        int snakeId = getSnakeIdByPlayerId(playerId);
        return gameState
                .getSnakes(snakeId)
                .getState();
    }

    public Integer getSnakeIdByPlayerId(int playerId) {
        List<GameState.Snake> snakes = gameState.getSnakesList();
        for (int i = 0; i < snakes.size(); i++) {
            if (snakes.get(i).getPlayerId() == playerId)
                return i;
        }
        return null;
    }

    public boolean addSnake(int playerId) {
        List<GameState.Coord> startCoords = findCoordsForNewSnake();
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
        return true;
    }

    private List<GameState.Coord> findCoordsForNewSnake() {
        List<GameState.Coord> emptyCoords = new GameStateDecorator(gameState).getAllEmptyCoords();
        List<GameState.Coord> answerList = new ArrayList<>();
        while (!emptyCoords.isEmpty()) {
            GameState.Coord currentCoord = emptyCoords.get(new Random().inBounds(0, emptyCoords.size() - 1));
            emptyCoords.remove(currentCoord);
            System.out.println(currentCoord);
            if (isCellNeighborhoodEmpty(currentCoord)) {
                answerList.add(currentCoord);
                answerList.add(checkOutOfBoundary(currentCoord.getX() + 1, currentCoord.getY()));
                break;
            }
        }
        return answerList;
    }

    private boolean isCellNeighborhoodEmpty(GameState.Coord currentCoord) {
        int coordX = currentCoord.getX();
        int coordY = currentCoord.getY();

        int fieldWidth = gameState.getConfig().getWidth();
        int fieldHeight = gameState.getConfig().getHeight();

        int xStart = coordX - NEIGHBOUR_COUNT < 0 ?
                fieldWidth - (NEIGHBOUR_COUNT - coordX)
                :
                coordX - NEIGHBOUR_COUNT;
        int yStart = coordY - NEIGHBOUR_COUNT < 0 ?
                fieldHeight - (NEIGHBOUR_COUNT - coordY)
                :
                coordY - NEIGHBOUR_COUNT;
        for (int i = xStart, count_i = 0; count_i < NEIGHBOUR_COUNT * NEIGHBOUR_COUNT + 1;
             count_i++, i = ((i + 1) == fieldWidth ? 0 : (i + 1))
        ) {
            for (int j = yStart, count_j = 0; count_j < NEIGHBOUR_COUNT * NEIGHBOUR_COUNT + 1;
                 count_j++, j = ((j + 1) == fieldHeight ? 0 : (j + 1))
            ) {
                if (!isCellEmpty(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isCellEmpty(int i, int j) {
        List<GameState.Coord> emptyCoords = new GameStateDecorator(gameState).getAllEmptyCoords();
        for (GameState.Coord currentCoord : emptyCoords) {
            if (currentCoord.getX() == i && currentCoord.getY() == j) {
                return true;
            }
        }
        return false;
    }

    public void deleteSnake(int playerId) {
        int snakeId = getSnakeIdByPlayerId(playerId);
        gameState = GameState.newBuilder(gameState)
                .removeSnakes(snakeId)
                .build();
    }
}

