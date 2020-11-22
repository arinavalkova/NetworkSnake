package graphics.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import graphics.data.NewGameWindowData;
import javafx.fxml.FXML;
import graphics.loaders.WindowNames;
import graphics.loaders.SceneLoader;

import java.io.IOException;

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

    private void loadDataFromWindow() {
        NewGameWindowData.setFieldWidth((int) widthSlider.getValue());
        NewGameWindowData.setFieldHeight((int) heightSlider.getValue());
    }

    private void addButtonsHandlers() {
        addBackButtonHandler();
        addPlayButtonHandler();
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

    private void addPlayButtonHandler() {
        playButton.setOnAction(e -> {
            try {
                loadDataFromWindow();
                SceneLoader.load(WindowNames.GAME_WINDOW);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
}
