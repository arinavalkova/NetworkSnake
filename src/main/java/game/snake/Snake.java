package game.snake;

import game.snake.mover.MoveDirection;
import game.snake.mover.SnakeMover;

import java.util.ArrayList;

public class Snake {
    private final ArrayList<Cell> snake;

    public Snake(Cell startCell) {
        snake = new ArrayList<>();
        snake.add(startCell);
    }

    public ArrayList<Cell> getCells() {
        return snake;
    }

    public void enlargeSnake(Cell cell) {
        snake.add(0, cell);//
    }

    public void moveSnake(MoveDirection moveDirection) {
        SnakeMover snakeMover = new SnakeMover(snake.get(0), moveDirection);
        snake.add(0, snakeMover.getCell());
        snake.remove(snake.size() - 1);
    }
}
