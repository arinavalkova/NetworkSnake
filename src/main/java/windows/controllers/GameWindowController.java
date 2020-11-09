package windows.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import main.Invariables;
import windows.SceneLoader;
import windows.SnakeCanvas;

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
        SnakeCanvas snakeCanvas = new SnakeCanvas(canvas);
        snakeCanvas.draw();
    }
}

