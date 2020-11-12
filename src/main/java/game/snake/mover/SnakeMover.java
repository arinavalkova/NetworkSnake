package game.snake.mover;

import game.food.FoodStorage;
import game.food.FoodUnit;
import game.Cell;
import game.snake.Snake;
import graphics.controllers.KeyController;

public class SnakeMover {
    private final KeyController keyController;
    private final FoodStorage foodStorage;
    private final Snake snake;
    private final int movingWidth;
    private final int movingHeight;

    public SnakeMover(int movingWidth, int movingHeight, KeyController keyController,
                      Snake snake, FoodStorage foodStorage) {
        this.movingWidth = movingWidth;
        this.movingHeight = movingHeight;
        this.keyController = keyController;
        this.foodStorage = foodStorage;
        this.snake = snake;
    }

    public Cell move(Cell headCell) {
        MoveDirection moveDirection = keyController.getKey();
        if (moveDirection == MoveDirection.RIGHT) {
            return checkOutOfBoundary(headCell.getX() + 1, headCell.getY());
        } else if (moveDirection == MoveDirection.LEFT) {
            return checkOutOfBoundary(headCell.getX() - 1, headCell.getY());
        } else if (moveDirection == MoveDirection.UP) {
            return checkOutOfBoundary(headCell.getX(), headCell.getY() - 1);
        }
        return checkOutOfBoundary(headCell.getX(), headCell.getY() + 1);
    }

    private Cell checkOutOfBoundary(int x, int y) {
        if (x < 0) {
            return new Cell(movingWidth - 1, y);
        } else if (x == movingWidth) {
            return new Cell(0, y);
        } else if (y < 0) {
            return new Cell(x, movingHeight - 1);
        } else if (y == movingHeight) {
            return new Cell(x, 0);
        }
        return new Cell(x, y);
    }

    public void start() {
        Cell cell = move(snake.getHead());
        if (foodStorage.findAndDelete(cell)) {
            snake.eat(cell);
        } else {
            snake.move(cell);
        }
    }
}
