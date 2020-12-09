package graphics.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import dto.GamePlayer;
import dto.NodeRole;
import game.multi.Server;
import game.multi.proto.renovators.GamePlayerRenovator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import graphics.loaders.WindowNames;
import graphics.loaders.SceneController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private Label infoLabel;

    @FXML
    private JFXListView<String> playersListView;

    @FXML
    private Label fieldSizeField;

    @FXML
    private Label foodField;

    @FXML
    private JFXButton backToMenuButton;

    private NodeRole nodeRole = NodeRole.NORMAL;
    private NodeRole pressedNodeRoleToHandle = null;

    @FXML
    public void initialize() {
        addChangeRoleButtonHandler();
        addBackButtonHandler();
    }

    private void addChangeRoleButtonHandler() {
        changeRoleButton.setOnAction(e -> {
            if (nodeRole == NodeRole.NORMAL) {
                Platform.runLater(() -> changeRoleButton.setText(PLAY_MODE));
                pressedNodeRoleToHandle = NodeRole.VIEWER;
                nodeRole = NodeRole.VIEWER;
            } else if (nodeRole == NodeRole.VIEWER) {
                Platform.runLater(() -> changeRoleButton.setText(VIEW_MODE));
                pressedNodeRoleToHandle = NodeRole.NORMAL;
                nodeRole = NodeRole.NORMAL;
            }
        });
    }

    public void setChangeRoleButton(NodeRole nodeRole) {
        if (nodeRole == NodeRole.VIEWER) {
            Platform.runLater(() -> changeRoleButton.setText(PLAY_MODE));
            this.nodeRole = NodeRole.VIEWER;
        } else if (nodeRole == NodeRole.NORMAL) {
            Platform.runLater(() -> changeRoleButton.setText(VIEW_MODE));
            this.nodeRole = NodeRole.NORMAL;
        }
    }

    public void setPlayersList(List<String> playersList) {
        Platform.runLater(() -> {
            playersListView.getItems().clear();
            playersListView.getItems().addAll(playersList);
        });
    }

    public void setFieldSize(int width, int height) {
        Platform.runLater(() -> fieldSizeField.setText(width + " X " + height));
    }

    private void addBackButtonHandler() {
        backToMenuButton.setOnAction(e -> {
            try {
                Server.stopGamePlay();
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
        return pressedNodeRoleToHandle;
    }

    public void changedRoleHandled() {
        pressedNodeRoleToHandle = null;
    }

    public void setFoodField(int food_static, float food_per_player) {
        Platform.runLater(() -> foodField.setText(food_static + PLUS + food_per_player + X));
    }

    public void updatePlayersList(ArrayList<GamePlayerRenovator> gamePlayerArrayList) {
        playersListView.getItems().clear();
        for (GamePlayerRenovator currentGamePlayer : gamePlayerArrayList) {
            playersListView.getItems().add(currentGamePlayer.toString());
        }
    }
}

