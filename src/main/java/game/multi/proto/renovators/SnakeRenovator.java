package game.multi.proto.renovators;

import dto.Direction;
import dto.GameState;
import game.multi.GamePlay;
import game.multi.proto.viewers.SnakeViewer;

import java.util.ArrayList;
import java.util.List;

public class SnakeRenovator {
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
        gameState = GameState.newBuilder(gameState)
                .removeSnakes(snakeId)
                .build();
        gamePlay.updateGameState(gameState);
    }
}

