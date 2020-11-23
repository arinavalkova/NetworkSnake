package graphics.loaders;

import graphics.controllers.KeyController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneController {
    private static Scene scene;
    private static GameFXMLLoader gameFxmlLoader;
    private static KeyController keyController;

    public static void loadMain(ClassLoader classLoader) throws IOException {
        gameFxmlLoader = new GameFXMLLoader(classLoader);
        FXMLLoader loader = gameFxmlLoader.getFXMLoader(WindowNames.START_WINDOW);
        Parent root = loader.load();
        SceneController.scene = new Scene(root);
        setKeyEvent();
    }

    private static void setKeyEvent() {
        keyController = new KeyController();
        getScene().setOnKeyPressed(event -> {
            keyController.setKeyPressed(event.getCode());
        });
    }

    public static FXMLLoader load(String FXMLName) throws IOException {
        FXMLLoader loader = gameFxmlLoader.getFXMLoader(FXMLName);
        Parent root = loader.load();
        scene.setRoot(root);
        return loader;
    }

    public static Scene getScene() {
        return scene;
    }

    public static GameFXMLLoader getGameFxmlLoader() {
        return gameFxmlLoader;
    }

    public static KeyController getKeyController() {
        return keyController;
    }
}
