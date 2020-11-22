package game;

import game.field.CellRole;
import game.field.GameField;
import game.food.FoodStorage;
import game.snake.Snake;
import game.snake.mover.SnakeMover;
import graphics.controllers.KeyController;
import graphics.drawers.GameFieldDrawer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game implements ActionListener {
    private final GameFieldDrawer gameFieldDrawer;
    private final SnakeMover snakeMover;
    private Timer timer;

    public Game(GameField gameField, GameFieldDrawer gameFieldDrawer, KeyController keyController) {
        this.gameFieldDrawer = gameFieldDrawer;
        Snake snake = new Snake(gameField);
        FoodStorage foodStorage = new FoodStorage(3, gameField);
        this.snakeMover = new SnakeMover(gameField, keyController, snake, foodStorage);
    }

    public void start() {
        gameFieldDrawer.drawField();
        timer = new Timer(500, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (snakeMover.start() == -1) {
            end();
            timer.stop();
            return;
        }
        gameFieldDrawer.redrawField();
        gameFieldDrawer.draw(CellRole.FOOD);
        gameFieldDrawer.draw(CellRole.SNAKE);
    }

    private void end() {
        gameFieldDrawer.drawEndOfGame();
    }
}