package graphics.controllers;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import main.Invariables;
import graphics.loaders.SceneLoader;

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
            SceneLoader.load(Invariables.NEW_GAME_WINDOW);
        });
    }

    private void addJoinGameButtonHandler() {
        joinGameButton.setOnAction(e -> {
            SceneLoader.load(Invariables.JOIN_WINDOW);
        });
    }

    private void addAboutGameHandler() {
        aboutGameButton.setOnAction(e -> {
            SceneLoader.load(Invariables.ABOUT_WINDOW);
        });
    }
}
