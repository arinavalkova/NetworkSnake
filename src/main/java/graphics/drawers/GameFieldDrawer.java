package graphics.drawers;

import game.field.Cell;
import game.field.CellRole;
import game.field.GameField;
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

    private final static int CELL_SIZE = 20;
    public static final double LINE_WIDTH = 0.2;

    private final GraphicsContext field;
    private final GameField gameField;

    public GameFieldDrawer(GraphicsContext field, GameField gameField) {
        this.gameField = gameField;
        this.field = field;
    }

    public void drawField() {
        field.setLineWidth(LINE_WIDTH);
        for (int i = 0; i <= gameField.getWidth(); i++) {
            field.strokeLine(i * CELL_SIZE, 0,
                    i * CELL_SIZE,gameField.getHeight() * CELL_SIZE);
        }
        for (int i = 0; i <= gameField.getHeight(); i++) {
            field.strokeLine(0, i * CELL_SIZE,
                    gameField.getWidth() * CELL_SIZE,i * CELL_SIZE);
        }
    }

    private void drawCell(Cell currentCell, String cellColor) {
        field.setFill(Paint.valueOf(cellColor));
        field.fillRect(currentCell.getX() * CELL_SIZE, currentCell.getY() * CELL_SIZE,
                CELL_SIZE, CELL_SIZE);
    }

    public void redrawField() {
        field.clearRect(0, 0, gameField.getWidth() * CELL_SIZE + 1, gameField.getHeight() * CELL_SIZE + 1);
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
        field.clearRect(0, 0, gameField.getWidth() * CELL_SIZE + 1, gameField.getHeight() * CELL_SIZE + 1);
        field.setFont(new Font(FONT_SIZE));
        field.fillText(END_OF_GAME, X_COORD_TEXT, Y_COORD_TEXT);
    }
}
