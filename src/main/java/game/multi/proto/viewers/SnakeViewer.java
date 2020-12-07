package game.multi.proto.viewers;

import dto.Direction;
import dto.GamePlayer;
import dto.GameState;

import java.util.List;

public class SnakeViewer {
    private final GameState gameState;

    public SnakeViewer(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState.Coord moveCoord(GameState.Coord headCoord, Direction snakeDirection, GameState gameState) {
        if (snakeDirection == Direction.RIGHT) {
            return checkOutOfBoundary(headCoord.getX() + 1, headCoord.getY(), gameState);
        } else if (snakeDirection == Direction.LEFT) {
            return checkOutOfBoundary(headCoord.getX() - 1, headCoord.getY(), gameState);
        } else if (snakeDirection == Direction.UP) {
            return checkOutOfBoundary(headCoord.getX(), headCoord.getY() - 1, gameState);
        }
        return checkOutOfBoundary(headCoord.getX(), headCoord.getY() + 1, gameState);
    }

    public GameState.Coord checkOutOfBoundary(int x, int y, GameState gameState) {
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

    public Direction getSnakeDirectionByPlayerId(int playerId) {
        int snakeId = getSnakeIdByPlayerId(playerId);
        return gameState
                .getSnakes(snakeId)
                .getHeadDirection();
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

    public GameState.Coord testSnakeMoveBySnakeId(int snakeId) {
        GameState.Coord headCoord = gameState.getSnakes(snakeId).getPoints(0);
        Direction snakeDirection = gameState.getSnakes(snakeId).getHeadDirection();
        return moveCoord(headCoord, snakeDirection, gameState);
    }

    public List<GameState.Coord> getSnakeCoords(Integer playerId) {
        int snakeId = new SnakeViewer(gameState).getSnakeIdByPlayerId(playerId);
        return gameState
                .getSnakes(snakeId)
                .getPointsList();
    }

    public GameState.Coord testSnakeMoveByPlayerId(int playerId) {
        int snakeId = new SnakeViewer(gameState).getSnakeIdByPlayerId(playerId);
        GameState.Coord headCoord = gameState.getSnakes(snakeId).getPoints(0);
        Direction snakeDirection = gameState.getSnakes(snakeId).getHeadDirection();
        return moveCoord(headCoord, snakeDirection, gameState);
    }

    public Direction getSnakeDirection(int playerId) {
        int snakeId = getSnakeIdByPlayerId(playerId);
        return gameState.getSnakes(snakeId).getHeadDirection();
    }
}
