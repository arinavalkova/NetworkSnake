package graphics.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import dto.GamePlayer;
import dto.NodeRole;
import game.multi.proto.decorators.GamePlayerDecorator;
import graphics.data.NewGameWindowData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import graphics.loaders.WindowNames;
import graphics.loaders.SceneController;

import java.io.IOException;
import java.util.ArrayList;

public class GameWindowController {

    private final static String VIEW_MODE = "View";
    private final static String PLAY_MODE = "Play";
    private final static String PLUS = "+";
    private final static String X = "x";

    @FXML
    private Button changeRoleButton;

    @FXML
    private Canvas canvas;

    @FXML
    private Label masterField;

    @FXML
    private JFXListView<String> playersListView;

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

    private void addBackButtonHandler() {
        backToMenuButton.setOnAction(e -> {
            try {
                SceneController.load(WindowNames.START_WINDOW);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public NodeRole getButtonNodeRole() {
        return nodeRole;
    }

    public void setMasterField(String masterName) {
        masterField.setText(masterName);
    }

    public void setFoodField(int food_static, float food_per_player) {
        foodField.setText(food_static + PLUS + food_per_player + X);
    }

    public void updatePlayersList(ArrayList<GamePlayerDecorator> gamePlayerArrayList) {
        playersListView.getItems().clear();
        for (GamePlayerDecorator currentGamePlayer : gamePlayerArrayList) {
            playersListView.getItems().add(currentGamePlayer.toString());
        }
    }
}

