package graphics.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import graphics.data.NewGameWindowData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import graphics.loaders.WindowNames;
import graphics.loaders.SceneController;

import java.io.IOException;

public class GameWindowController {

    @FXML
    private Label pointsField;

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
        setFieldsWithData();
        setPoints(0);
    }

    private void setFieldsWithData() {
        foodField.setText(String.valueOf(NewGameWindowData.getFoodStatic()));
        fieldSizeField.setText(NewGameWindowData.getFieldWidth() +
                " X " + NewGameWindowData.getFieldHeight());
    }

    private void addBackButtonHandler() {
        backToMenuButton.setOnAction(e -> {
            try {
                SceneController.load(WindowNames.START_WINDOW);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public void setPoints(int points) {
        Platform.runLater(() -> pointsField.setText(String.valueOf(points)));
    }

    public Canvas getCanvas() {
        return canvas;
    }
}

