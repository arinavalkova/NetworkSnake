package game.single;

import game.single.field.CellRole;
import game.single.field.GameField;
import game.single.food.FoodStorage;
import game.single.snake.Snake;
import game.single.snake.mover.SnakeMover;
import graphics.controllers.GameWindowController;
import graphics.controllers.KeyController;
import graphics.drawers.GameFieldDrawer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SingleGame implements ActionListener {
    private final GameWindowController gameWindowController;
    private final GameFieldDrawer gameFieldDrawer;
    private final SnakeMover snakeMover;
    private Timer timer;
    private final int timerDelay;

    public SingleGame(GameWindowController gameWindowController, int fieldWidth, int fieldHeight,
                      KeyController keyController, int timerDelay, int foodCount) {
        GameField gameField = new GameField(fieldWidth, fieldHeight);
        GameFieldDrawer gameFieldDrawer =
                new GameFieldDrawer(gameWindowController.getCanvas().getGraphicsContext2D(), gameField);
        this.gameWindowController = gameWindowController;
        this.gameFieldDrawer = gameFieldDrawer;
        this.timerDelay = timerDelay;
        Snake snake = new Snake(gameField);
        FoodStorage foodStorage = new FoodStorage(foodCount, gameField);
        this.snakeMover = new SnakeMover(gameField, keyController, snake, foodStorage);
    }

    public void start() {
        gameFieldDrawer.drawField();
        timer = new Timer(timerDelay, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int points;
        if ((points = snakeMover.start()) == -1) {
            end();
            timer.stop();
            return;
        }
        gameWindowController.setPoints(points);
        gameFieldDrawer.redrawField();
        gameFieldDrawer.draw(CellRole.FOOD);
        gameFieldDrawer.draw(CellRole.SNAKE);
    }

    private void end() {
        gameFieldDrawer.drawEndOfGame();
    }
}