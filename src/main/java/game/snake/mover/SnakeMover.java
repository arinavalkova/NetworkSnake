package game.snake.mover;

import game.snake.Cell;
import java.util.Objects;

public class SnakeMover {
    private final static MoveDirection defaultMoveDirection = MoveDirection.UP;
    private final MoveDirection userMoveDirection;
    private final Cell headCell;

    public SnakeMover(Cell headCell, MoveDirection userMoveDirection) {
        this.headCell = headCell;
        this.userMoveDirection = userMoveDirection;
    }

    public Cell getCell() {
        MoveDirection currentMoveDirection = getCurrentMoveDirection();
        return moveAndGetCell(currentMoveDirection);
    }

    private MoveDirection getCurrentMoveDirection() {
        return Objects.requireNonNullElse(userMoveDirection, defaultMoveDirection);
    }

    private Cell moveAndGetCell(MoveDirection moveDirection) {
        if (moveDirection == MoveDirection.RIGHT) {
            return new Cell(headCell.getX() + 1, headCell.getY());
        } else if (moveDirection == MoveDirection.LEFT) {
            return new Cell(headCell.getX() - 1, headCell.getY());
        } else if (moveDirection == MoveDirection.UP) {
            return new Cell(headCell.getX(), headCell.getY() + 1);
        }
        return new Cell(headCell.getX(), headCell.getY() - 1);
    }
}
