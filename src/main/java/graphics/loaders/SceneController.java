package graphics.loaders;

import graphics.controllers.KeyController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneController {
    private static Scene scene;
    private static GameFXMLLoader gameFxmlLoader;

    public static void loadMain(ClassLoader classLoader) throws IOException {
        gameFxmlLoader = new GameFXMLLoader(classLoader);
        FXMLLoader loader = gameFxmlLoader.getFXMLoader(WindowNames.START_WINDOW);
        Parent root = loader.load();
        SceneController.scene = new Scene(root);
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
}
