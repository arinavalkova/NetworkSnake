package graphics.controllers;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import graphics.loaders.WindowNames;
import graphics.loaders.SceneController;

import java.io.IOException;

public class AboutWindowController {

    @FXML
    private JFXButton backButton;

    @FXML
    void initialize() {
        backButton.setOnAction(e -> {
            try {
                SceneController.load(WindowNames.START_WINDOW);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
}