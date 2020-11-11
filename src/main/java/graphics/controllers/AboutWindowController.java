package graphics.controllers;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import main.Invariables;
import graphics.loaders.SceneLoader;

public class AboutWindowController {

    @FXML
    private JFXButton backButton;

    @FXML
    void initialize() {
        backButton.setOnAction(e -> {
            SceneLoader.load(Invariables.START_WINDOW);
        });
    }
}
