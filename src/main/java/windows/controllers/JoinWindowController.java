package windows.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import main.Invariables;
import windows.SceneLoader;

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
            SceneLoader.load(Invariables.START_WINDOW);
        });
    }

    private void addJoinButtonHandler() {
        joinButton.setOnAction(e -> {
            SceneLoader.load(Invariables.GAME_WINDOW);
        });
    }
}
