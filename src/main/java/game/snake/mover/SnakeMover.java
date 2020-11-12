package game.snake.mover;

import game.snake.Cell;
import graphics.controllers.KeyController;

public class SnakeMover {
    private final KeyController keyController;
    private final int movingWidth;
    private final int movingHeight;

    public SnakeMover(int movingWidth, int movingHeight, KeyController keyController) {
        this.movingWidth = movingWidth;
        this.movingHeight = movingHeight;
        this.keyController = keyController;
    }

    public Cell getCell(Cell headCell) {
        MoveDirection moveDirection = keyController.getKey();
        return moveAndGetCell(headCell, moveDirection);
    }

    private Cell moveAndGetCell(Cell headCell, MoveDirection moveDirection) {
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
}
