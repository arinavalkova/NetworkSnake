package graphics.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import game.Game;
import game.field.GameField;
import graphics.data.NewGameWindowData;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import graphics.loaders.WindowNames;
import graphics.loaders.SceneLoader;
import graphics.drawers.GameFieldDrawer;

import java.io.IOException;

public class GameWindowController {

    public static final Integer SNAKE_FIELD_WIDTH = 30;
    public static final Integer SNAKE_FIELD_HEIGHT = 30;

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
        addCanvasHandler();
    }

    private void addCanvasHandler() {
        setSnakeCanvas();
    }

    private void addBackButtonHandler() {
        backToMenuButton.setOnAction(e -> {
            try {
                SceneLoader.load(WindowNames.START_WINDOW);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void setSnakeCanvas() {
        KeyController keyController = new KeyController();
        SceneLoader.getScene().setOnKeyPressed(event -> {
            keyController.setKeyPressed(event.getCode());
        });

        GameField gameField = new GameField(NewGameWindowData.getFieldWidth(),
                NewGameWindowData.getFieldHeight());
        GameFieldDrawer gameFieldDrawer = new GameFieldDrawer(canvas.getGraphicsContext2D(), gameField);
        Game game = new Game(gameField, gameFieldDrawer, keyController);
        game.start();
    }
}

