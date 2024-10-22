package graphics.drawers;

import dto.GameState;
import game.multi.GamePlay;
import game.multi.proto.renovators.SnakeRenovator;
import game.multi.proto.viewers.GameStateViewer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.List;

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
    private final int cellSize;

    public GameFieldDrawer(GraphicsContext field, GamePlay gamePlay) {
        GameState gameState = gamePlay.getGameState();
        this.field = field;
        this.cellSize = gameState.getConfig().getHeight() > gameState.getConfig().getWidth() ?
                FIELD_HEIGHT / gameState.getConfig().getHeight()
                :
                FIELD_WIDTH / gameState.getConfig().getWidth();
    }

    public void drawField(GameState gameState) {
        field.setLineWidth(LINE_WIDTH);
        for (int i = 0; i <= gameState.getConfig().getWidth(); i++) {
            field.strokeLine(i * cellSize, 0,
                    i * cellSize,gameState.getConfig().getHeight() * cellSize);
        }
        for (int i = 0; i <= gameState.getConfig().getHeight(); i++) {
            field.strokeLine(0, i * cellSize,
                    gameState.getConfig().getWidth() * cellSize,i * cellSize);
        }
    }

    private void drawCell(GameState.Coord currentCoord, String cellColor) {
        field.setFill(Paint.valueOf(cellColor));
//        field.fillRect(currentCell.getX() * CELL_SIZE, currentCell.getY() * CELL_SIZE,
//                CELL_SIZE, CELL_SIZE);
        field.fillOval(currentCoord.getX() * cellSize, currentCoord.getY() * cellSize,
                cellSize, cellSize);
    }

    public void redrawField(GamePlay gamePlay) {
        clear(gamePlay.getGameState());
        drawField(gamePlay.getGameState());
        drawFood(gamePlay);
        drawSnakes(gamePlay);
    }

    private void drawSnakes(GamePlay gamePlay) {
        List<GameState.Snake> allSnakesWithoutMySnake =
                new GameStateViewer(gamePlay.getGameState()).getAllSnakes();
        for (GameState.Snake currentSnake : allSnakesWithoutMySnake) {
            drawSnake(currentSnake, DrawerColor.SNAKES, DrawerColor.HEAD_SNAKES);
        }
        GameState.Snake mySnake = new SnakeRenovator(gamePlay).getMySnake();
        if (mySnake != null) {
            drawSnake(mySnake, DrawerColor.MY_SNAKE, DrawerColor.HEAD_MY_SNAKE);
        }
    }

    private void drawSnake(GameState.Snake snake, String snakeColor, String headColor) {
        List<GameState.Coord> snakeCoords = snake.getPointsList();
        for (GameState.Coord currentCoord : snakeCoords) {
            drawCell(currentCoord, snakeColor);
        }
        drawCell(snakeCoords.get(0), headColor);
    }

    private void drawFood(GamePlay gamePlay) {
        List<GameState.Coord> foodCoords = new GameStateViewer(gamePlay.getGameState()).getAllFoodsCoords();
        for (GameState.Coord currentCoord : foodCoords) {
                drawCell(currentCoord, DrawerColor.FOOD);
        }
    }

    public void drawEndOfGame(GameState gameState) {
        field.setFill(Paint.valueOf(END_OF_GAME_COLOR));
        clear(gameState);
        clear(gameState);
        field.setFont(new Font(FONT_SIZE));
        field.fillText(END_OF_GAME, X_COORD_TEXT, Y_COORD_TEXT);
    }

    public void clear(GameState gameState) {
        field.clearRect(0, 0, gameState.getConfig().getWidth() * cellSize + 1,
                gameState.getConfig().getHeight() * cellSize + 1);
    }
}
