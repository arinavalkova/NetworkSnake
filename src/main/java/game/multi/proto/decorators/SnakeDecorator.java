package game.multi.proto.decorators;

import dto.Direction;
import dto.GameState;

import java.util.ArrayList;
import java.util.List;

public class SnakeDecorator {
    private GameState.Snake snake;

    public SnakeDecorator(GameState.Snake snake) {
        this.snake = snake;
    }

    public void updateState(GameState.Snake.SnakeState snakeState) {
        snake = GameState.Snake.newBuilder(snake)
                .setState(snakeState)
                .build();
    }

    public void updateCoordsList(ArrayList<GameState.Coord> coords) {//////////////
        GameState.Snake.Builder snakeBuilder = GameState.Snake.newBuilder(snake).clearPoints();
        for (int i = 0; i < coords.size(); i++){
            snakeBuilder.setPoints(i, coords.get(i));
        }
        snake = snakeBuilder.build();
    }

    public void updateHeadDirection(Direction direction) {
        snake = GameState.Snake.newBuilder(snake)
                .setHeadDirection(direction)
                .build();
    }

    public void moveSnake(GameState.Coord moveCoord) {
        List<GameState.Coord> coords = snake.getPointsList();
        coords.add(0, moveCoord);
        coords.remove(coords.size() - 1);
        snake = GameState.Snake.newBuilder(snake)
                .clearPoints()
                .addAllPoints(coords)
                .build();
    }

    //eat
}