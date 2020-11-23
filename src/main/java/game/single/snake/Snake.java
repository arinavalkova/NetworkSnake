package game.single.snake;

import game.single.field.Cell;
import game.single.field.CellRole;
import game.single.field.GameField;

import java.util.ArrayList;

public class Snake {
    private final ArrayList<Cell> snake;
    private final GameField gameField;

    public Snake(GameField gameField) {
        this.snake = new ArrayList<>();
        this.gameField = gameField;
        create();
    }

    public void create() {
        createTailCell(createHeadCell());
    }

    private Cell createHeadCell() {
        Cell headCell = gameField.getCenterOfFreeArea();
        add(snake.size(), headCell);
        return headCell;
    }

    private void createTailCell(Cell firstCell) {
        Cell tailCell = gameField.getCell(firstCell.getX(), firstCell.getY() + 1);
        add(snake.size(), tailCell);
    }

    private void add(int position, Cell cell) {
        snake.add(position, cell);
        cell.setRole(CellRole.SNAKE);
        gameField.incSnakeCellsCount();
    }

    public void eat(Cell cell) {
        add(0, cell);
    }

    public void move(Cell cell) {
        add(0, cell);
        removeCell(snake.get(snake.size() - 1));
    }

    private void removeCell(Cell cell) {
        Cell gameFieldCell = gameField.getCell(cell.getX(), cell.getY());
        gameFieldCell.removeRole(CellRole.SNAKE);
        snake.remove(cell);
        gameField.decSnakeCellsCount();
    }

    public Cell getHead() {
        return snake.get(0);
    }
}
