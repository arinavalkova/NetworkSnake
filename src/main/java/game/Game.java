package game;

import game.snake.Cell;
import game.snake.Snake;
import game.snake.mover.MoveDirection;
import game.snake.mover.SnakeMover;
import graphics.controllers.KeyController;
import graphics.drawers.GameFieldDrawer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game implements ActionListener {
    private final GameFieldDrawer gameFieldDrawer;
    private final Snake snake;

    public Game(GameFieldDrawer gameFieldDrawer, KeyController keyController) {
        this.gameFieldDrawer = gameFieldDrawer;
        SnakeMover snakeMover = new SnakeMover(gameFieldDrawer.getWidth(), gameFieldDrawer.getHeight(),
                keyController);
        this.snake = new Snake(new Cell(10, 10), snakeMover);
    }

    public void start() {
        gameFieldDrawer.drawField();
        Timer timer = new Timer(2000, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        snake.moveSnake();
        gameFieldDrawer.redraw(snake.getCells());
    }
}
