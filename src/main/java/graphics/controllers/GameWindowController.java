package graphics.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import dto.NodeRole;
import graphics.data.NewGameWindowData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import graphics.loaders.WindowNames;
import graphics.loaders.SceneController;

import java.io.IOException;

public class GameWindowController {

    private final static String VIEW_MODE = "View";
    private final static String PLAY_MODE = "Play";

    @FXML
    private Button changeRoleButton;

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

    private NodeRole nodeRole = null;

    @FXML
    public void initialize() {
        addChangeRoleButtonHandler();
        addBackButtonHandler();
        setPoints(0);
    }

    private void addChangeRoleButtonHandler() {
        changeRoleButton.setOnAction(e -> {
            if (nodeRole == null || nodeRole == NodeRole.NORMAL) {
                changeRoleButton.setText(PLAY_MODE);
                nodeRole = NodeRole.VIEWER;
            } else if (nodeRole == NodeRole.VIEWER) {
                changeRoleButton.setText(VIEW_MODE);
                nodeRole = NodeRole.NORMAL;
            }
        });
    }

    public void setFieldSize(int width, int height) {
        Platform.runLater(() ->fieldSizeField.setText(width + " X " + height));
    }

    //foodField setter

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

    public NodeRole getButtonNodeRole() {
        return nodeRole;
    }
}

