package graphics.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import game.Game;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import main.Invariables;
import graphics.loaders.SceneLoader;
import graphics.drawers.GameFieldDrawer;

public class GameWindowController {

    @FXML
    private Canvas canvas;

    @FXML
    private Label hostField;

    @FXML
    private JFXListView<?> playersListView;

    @FXML
    private Label fieldSizeField;

    @FXML
    private Label foodField;

    @FXML
    private JFXButton backToMenuButton;

    @FXML
    public void initialize() {
        addBackButtonHandler();
        setSnakeCanvas();
    }

    private void addBackButtonHandler() {
        backToMenuButton.setOnAction(e -> {
            SceneLoader.load(Invariables.START_WINDOW);
        });
    }

    private void setSnakeCanvas() {
        GameFieldDrawer gameFieldDrawer = new GameFieldDrawer(canvas.getGraphicsContext2D(),
                Invariables.SNAKE_FIELD_WIDTH, Invariables.SNAKE_FIELD_HEIGHT);
        Game game = new Game(gameFieldDrawer);
        game.start();
    }
}

