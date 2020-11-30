package game.multi.proto.decorators;

import dto.Direction;
import dto.GameState;

import java.util.ArrayList;
import java.util.List;

public class SnakeDecorator {
    private GameState gameState;

    public SnakeDecorator(GameState gameState) {
        this.gameState = gameState;
    }

//    public void updateState(GameState.Snake.SnakeState snakeState) {
//        snake = GameState.Snake.newBuilder(snake)
//                .setState(snakeState)
//                .build();
//    }
//
//    public void updateCoordsList(ArrayList<GameState.Coord> coords) {//////////////
//        GameState.Snake.Builder snakeBuilder = GameState.Snake.newBuilder(snake).clearPoints();
//        for (int i = 0; i < coords.size(); i++){
//            snakeBuilder.setPoints(i, coords.get(i));
//        }
//        snake = snakeBuilder.build();
//    }
//
//    public void updateHeadDirection(Direction direction) {
//        snake = GameState.Snake.newBuilder(snake)
//                .setHeadDirection(direction)
//                .build();
//    }
//
//    public void moveSnake(GameState.Coord moveCoord) {
//        List<GameState.Coord> coords = snake.getPointsList();
//        coords.add(0, moveCoord);
//        coords.remove(coords.size() - 1);
//        snake = GameState.Snake.newBuilder(snake)
//                .clearPoints()
//                .addAllPoints(coords)
//                .build();
//    }

    public void addSnake() {

    }

    public void deleteSnake(int snakeId) {

    }

    public void snakeMove(int snakeId, int x, int y) {

    }

    public void snakeEat(int snakeId, int x, int y) {

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

    //ищем совпадение клетки с какой-то другой змеей, возвращаем либо нулл либо id_player змеи

    //продвижение зомби змеек, возвращение той же map
}

