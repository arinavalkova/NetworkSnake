package graphics.drawers;

import game.single.field.Cell;
import game.single.field.CellRole;
import game.single.field.GameField;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class GameFieldDrawer {
    private final static String END_OF_GAME = "End of game";
    private final static String END_OF_GAME_COLOR = "#273a4f";
    private final static int FONT_SIZE = 24;
    private final static int X_COORD_TEXT = 270;
    private final static int Y_COORD_TEXT = 300;
    private final static int FIELD_WIDTH = 600;
    private final static int FIELD_HEIGHT = 600;
    public static final double LINE_WIDTH = 0.2;

    private final GraphicsContext field;
    private final GameField gameField;
    private final int cellSize;

    public GameFieldDrawer(GraphicsContext field, GameField gameField) {
        this.gameField = gameField;
        this.field = field;
        this.cellSize = gameField.getHeight() > gameField.getWidth() ?
                FIELD_HEIGHT / gameField.getHeight()
                :
                FIELD_WIDTH / gameField.getWidth();
    }

    public void drawField() {
        field.setLineWidth(LINE_WIDTH);
        for (int i = 0; i <= gameField.getWidth(); i++) {
            field.strokeLine(i * cellSize, 0,
                    i * cellSize,gameField.getHeight() * cellSize);
        }
        for (int i = 0; i <= gameField.getHeight(); i++) {
            field.strokeLine(0, i * cellSize,
                    gameField.getWidth() * cellSize,i * cellSize);
        }
    }

    private void drawCell(Cell currentCell, String cellColor) {
        field.setFill(Paint.valueOf(cellColor));
//        field.fillRect(currentCell.getX() * CELL_SIZE, currentCell.getY() * CELL_SIZE,
//                CELL_SIZE, CELL_SIZE);
        field.fillOval(currentCell.getX() * cellSize, currentCell.getY() * cellSize,
                cellSize, cellSize);
    }

    public void redrawField() {
        clear();
        drawField();
    }

    public void draw(CellRole cellRole) {
        ArrayList<Cell> cells = gameField.getGameField();
        for (Cell currentCell : cells) {
            if (currentCell.findRole(cellRole)) {
                drawCell(currentCell, DrawerColor.getColor(cellRole));
            }
        }
    }

    public void drawEndOfGame() {
        field.setFill(Paint.valueOf(END_OF_GAME_COLOR));
        clear();
        field.setFont(new Font(FONT_SIZE));
        field.fillText(END_OF_GAME, X_COORD_TEXT, Y_COORD_TEXT);
    }

    public void clear() {
        field.clearRect(0, 0, gameField.getWidth() * cellSize + 1, gameField.getHeight() * cellSize + 1);
    }
}
