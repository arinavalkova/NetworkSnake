package graphics.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import dto.NodeRole;
import game.multi.Server;
import graphics.data.JoinGameWindowData;
import graphics.loaders.SceneController;
import graphics.loaders.WindowNames;
import javafx.application.Platform;
import javafx.fxml.FXML;
import main.TimeOut;

import java.io.IOException;

public class JoinWindowController {

    private final static int GAMES_LIST_TIME_OUT = 5000;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton joinButton;

    @FXML
    private JFXCheckBox viewerModeCheckBox;

    @FXML
    private JFXListView<String> gamesListView;


    @FXML
    void initialize() {
        updateGamesList();
        addButtonsHandlers();
    }

    private void updateGamesList() {
        Thread updateGameListThread = new Thread(() -> {
            while (!Server.isStopped()) {
                    System.out.println("printing");
                    Platform.runLater(() -> {
                        gamesListView.getItems().clear();
                        gamesListView.getItems().addAll(Server
                                .getReceiverFactory()
                                .getCurrentGames()
                                .getCurrentGames()
                        );
                    });
                    new TimeOut(GAMES_LIST_TIME_OUT).start();
            }
        });
        updateGameListThread.start();
    }

    private void addButtonsHandlers() {
        addBackButtonHandler();
        addJoinButtonHandler();
    }

    private void addBackButtonHandler() {
        backButton.setOnAction(e -> {
            try {
                SceneController.load(WindowNames.START_WINDOW);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void addJoinButtonHandler() {
        joinButton.setOnAction(e -> {
            loadDataFromWindow();
            try {
                Server.joinGame(SceneController.load(
                        WindowNames.GAME_WINDOW
                ).getController(), viewerModeCheckBox.isSelected() ?
                        NodeRole.VIEWER
                        :
                        NodeRole.NORMAL);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void loadDataFromWindow() {
        JoinGameWindowData.setName(nameField.getText());
    }
}
