package graphics.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import graphics.loaders.WindowNames;
import graphics.loaders.SceneLoader;

import java.io.IOException;

public class JoinWindowController {

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton joinButton;

    @FXML
    private JFXTextField ipField;

    @FXML
    private JFXTextField portField;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXCheckBox viewerModeCheckBox;

    @FXML
    void initialize() {
        addButtonsHandlers();
    }

    private void addButtonsHandlers() {
        addBackButtonHandler();
        addJoinButtonHandler();
    }

    private void addBackButtonHandler() {
        backButton.setOnAction(e -> {
            try {
                SceneLoader.load(WindowNames.START_WINDOW);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void addJoinButtonHandler() {
        joinButton.setOnAction(e -> {
            try {
                SceneLoader.load(WindowNames.GAME_WINDOW);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
}
