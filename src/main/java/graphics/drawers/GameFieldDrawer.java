package graphics.drawers;

import game.snake.Cell;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class GameFieldDrawer {
    private static final String SNAKE_CELL_COLOR = "#64b5f6";
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

    public void drawCellsCollection(ArrayList<Cell> cells) {
        for (Cell currentCell : cells) {
            drawCell(currentCell, SNAKE_CELL_COLOR);
        }
    }

    private void drawCell(Cell currentCell, String cellColor) {
        field.setFill(Paint.valueOf(cellColor));
        field.fillRect(currentCell.getX() * CELL_SIZE, currentCell.getY() * CELL_SIZE,
                CELL_SIZE, CELL_SIZE);
    }

    public void redraw(ArrayList<Cell> cells) {
        clearField();
        drawField();
        drawCellsCollection(cells);
    }

    private void clearField() {
        field.clearRect(0, 0, width * CELL_SIZE, height * CELL_SIZE);
    }
}
