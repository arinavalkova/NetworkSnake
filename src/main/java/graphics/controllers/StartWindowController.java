package graphics.controllers;
import com.jfoenix.controls.JFXButton;
import game.multi.ServerGame;
import javafx.fxml.FXML;
import graphics.loaders.WindowNames;
import graphics.loaders.SceneController;

import java.io.IOException;

public class StartWindowController {

    @FXML
    private JFXButton newGameButton;

    @FXML
    private JFXButton joinGameButton;

    @FXML
    private JFXButton aboutGameButton;

    @FXML
    void initialize() {
        addNewGameButtonHandler();
        addJoinGameButtonHandler();
        addAboutGameHandler();
    }

    private void addNewGameButtonHandler() {
        newGameButton.setOnAction(e -> {
            try {
                SceneController.load(WindowNames.NEW_GAME_WINDOW);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void addJoinGameButtonHandler() {
        joinGameButton.setOnAction(e -> {
            try {
                SceneController.load(WindowNames.JOIN_WINDOW);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void addAboutGameHandler() {
        aboutGameButton.setOnAction(e -> {
            try {
                SceneController.load(WindowNames.ABOUT_WINDOW);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
}
