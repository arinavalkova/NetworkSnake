package game.snake;

import game.Cell;

import java.util.ArrayList;

public class Snake {
    private final ArrayList<Cell> snake;

    public Snake(Cell firstCell) {
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

    public void eat(Cell cell) {
        snake.add(0, cell);
    }

    public void move(Cell cell) {
        snake.add(0, cell);
        snake.remove(snake.size() - 1);
    }

    public Cell getHead() {
        return snake.get(0);
    }
}
