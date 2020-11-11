package game;

import game.snake.Cell;
import game.snake.Snake;
import game.snake.mover.MoveDirection;
import game.snake.mover.SnakeMover;
import graphics.drawers.GameFieldDrawer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game implements ActionListener {
    private final GameFieldDrawer gameFieldDrawer;
    private final Snake snake;

    public Game(GameFieldDrawer gameFieldDrawer) {
        this.gameFieldDrawer = gameFieldDrawer;
        this.snake = new Snake(new Cell(10, 10));
    }

    public void start() {
        gameFieldDrawer.drawField();

        Timer timer = new Timer(1000, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameFieldDrawer.redraw(snake.getCells());
        snake.moveSnake(MoveDirection.DOWN);
    }
}
