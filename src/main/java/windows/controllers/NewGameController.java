package windows.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import main.Invariables;
import windows.SceneLoader;

public class NewGameController {

    @FXML
    private JFXSlider widthSlider;

    @FXML
    private JFXSlider heightSlider;

    @FXML
    private JFXTextField portField;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton playButton;

    @FXML
    private JFXTextField nameField;

    @FXML
    void initialize() {
        addButtonsHandlers();
    }

    private void addButtonsHandlers() {
        addBackButtonHandler();
        addPlayButtonHandler();
    }

    private void addBackButtonHandler() {
        backButton.setOnAction(e -> {
            SceneLoader.load(Invariables.START_WINDOW);
        });
    }

    private void addPlayButtonHandler() {
        playButton.setOnAction(e -> {
            SceneLoader.load(Invariables.GAME_WINDOW);
        });
    }
}
