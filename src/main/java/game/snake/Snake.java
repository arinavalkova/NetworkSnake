package game.snake;

import java.util.ArrayList;

public class Snake {
    private final ArrayList<Cell> snake;

    public Snake(Cell startCell) {
        snake = new ArrayList<>();
        snake.add(startCell);
    }

    public ArrayList<Cell> getSnake() {
        return snake;
    }
}
