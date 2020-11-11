package graphics.drawers;

import game.snake.Cell;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class GameFieldDrawer {
    private static final String CELL_COLOR = "blue";
    private final static int CELL_SIZE = 20;
    public static final double LINE_WIDTH = 0.2;

    private final GraphicsContext field;
    private final int width;
    private final int height;

    public GameFieldDrawer(GraphicsContext field, int fieldWidth, int fieldHeight) {
        this.width = fieldWidth;
        this.height = fieldHeight;
        this.field = field;
    }

    public void drawField() {
        field.setLineWidth(LINE_WIDTH);
        for (int i = 0; i <= width; i++) {
            field.strokeLine(0, i * CELL_SIZE,
                    height * CELL_SIZE, i * CELL_SIZE);
        }
        for (int i = 0; i <= height; i++) {
            field.strokeLine(i * CELL_SIZE, 0, i * CELL_SIZE,
                    width * CELL_SIZE);
        }
    }

    public void drawSnake(ArrayList<Cell> snakeCells) {
        for (Cell currentCell : snakeCells) {
            drawCell(currentCell);
        }
    }

    private void drawCell(Cell currentCell) {
        field.setFill(Paint.valueOf(CELL_COLOR));
        field.fillRect(currentCell.getX(), currentCell.getY(), CELL_SIZE, CELL_SIZE);
    }
}
