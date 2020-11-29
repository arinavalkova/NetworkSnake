package game.multi.snake.mover;

import dto.Direction;
import game.multi.field.Cell;
import game.multi.field.CellRole;
import game.multi.field.GameField;
import game.multi.food.FoodStorage;
import game.multi.snake.Snake;
import graphics.controllers.KeyController;

public class SnakeMover {
    private final KeyController keyController;
    private final FoodStorage foodStorage;
    private final Snake snake;
    private final GameField gameField;
    private int points;

    public SnakeMover(GameField gameField, KeyController keyController,
                      Snake snake, FoodStorage foodStorage) {
        this.gameField = gameField;
        this.keyController = keyController;
        this.foodStorage = foodStorage;
        this.snake = snake;
        points = 0;
    }

    public Cell move(Cell headCell) {
        Direction moveDirection = keyController.getKey();
        if (moveDirection == Direction.RIGHT) {
            return checkOutOfBoundary(headCell.getX() + 1, headCell.getY());
        } else if (moveDirection == Direction.LEFT) {
            return checkOutOfBoundary(headCell.getX() - 1, headCell.getY());
        } else if (moveDirection == Direction.UP) {
            return checkOutOfBoundary(headCell.getX(), headCell.getY() - 1);
        }
        return checkOutOfBoundary(headCell.getX(), headCell.getY() + 1);
    }

    private Cell checkOutOfBoundary(int x, int y) {
        if (x < 0) {
            return gameField.getCell(gameField.getWidth() - 1, y);
        } else if (x == gameField.getWidth()) {
            return gameField.getCell(0, y);
        } else if (y < 0) {
            return gameField.getCell(x, gameField.getHeight() - 1);
        } else if (y == gameField.getHeight()) {
            return gameField.getCell(x, 0);
        }
        return gameField.getCell(x, y);
    }

    public int start() {
        Cell cell = move(snake.getHead());
        if (cell.findRole(CellRole.SNAKE)) return -1;
        if (foodStorage.findAndDelete(cell)) {
            snake.eat(cell);
            points++;
        } else {
            snake.move(cell);
        }
        return points;
    }
}
