package graphics.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import game.multi.ServerGame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import graphics.loaders.WindowNames;
import graphics.loaders.SceneController;

import java.io.IOException;
import java.util.ArrayList;

public class JoinWindowController {

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
            /* TO DO */
            while (true) {
                try {
                    Platform.runLater(() -> {
                        gamesListView.getItems().clear();
                        gamesListView.getItems().add(ServerGame.getCurrentGames());
                    });
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /*TO DO*/
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
            try {
                loadDataFromWindow();
                ServerGame.joinGame(SceneController.load(
                        WindowNames.GAME_WINDOW
                ).getController());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void loadDataFromWindow() {

    }
}
