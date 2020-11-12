package game.snake;

import game.snake.mover.MoveDirection;
import game.snake.mover.SnakeMover;

import java.util.ArrayList;

public class Snake {
    private final ArrayList<Cell> snake;
    private final SnakeMover snakeMover;

    public Snake(Cell firstCell, SnakeMover snakeMover) {
        this.snakeMover = snakeMover;
        snake = new ArrayList<>();
        snake.add(firstCell);
        snake.add(createTailCell(firstCell));
    }

    private Cell createTailCell(Cell firstCell) {
        return new Cell(firstCell.getX(), firstCell.getY() + 1);
    }

    public ArrayList<Cell> getCells() {
        return snake;
    }

    public void enlargeSnake(Cell cell) {
        snake.add(0, cell);//
    }

    public void moveSnake() {
        snake.add(0, snakeMover.getCell(snake.get(0)));
        snake.remove(snake.size() - 1);
    }
}
