package game;

import game.food.FoodStorage;
import game.snake.Snake;
import game.snake.mover.SnakeMover;
import graphics.controllers.KeyController;
import graphics.drawers.DrawerColor;
import graphics.drawers.GameFieldDrawer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game implements ActionListener {
    private final GameFieldDrawer gameFieldDrawer;
    private final SnakeMover snakeMover;
    private final Snake snake;
    private final FoodStorage foodStorage;

    public Game(GameFieldDrawer gameFieldDrawer, KeyController keyController) {
        this.gameFieldDrawer = gameFieldDrawer;
        this.snake = new Snake(new Cell(10, 10));
        this.foodStorage = new FoodStorage(3, gameFieldDrawer.getWidth(),
                gameFieldDrawer.getHeight());
        this.snakeMover = new SnakeMover(gameFieldDrawer.getWidth(), gameFieldDrawer.getHeight(),
                keyController, snake, foodStorage);
    }

    public void start() {
        gameFieldDrawer.drawField();
        Timer timer = new Timer(500, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        snakeMover.start();
        gameFieldDrawer.redrawField();
        gameFieldDrawer.draw(foodStorage.getCells(), DrawerColor.FOOD);
        gameFieldDrawer.draw(snake.getCells(), DrawerColor.SNAKE);
    }
}
