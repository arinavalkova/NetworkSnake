package graphics.loaders;

import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneLoader {
    private static Scene scene;
    private static FXMLLoader fxmlLoader;

    public static void loadMain(ClassLoader classLoader) throws IOException {
        fxmlLoader = new FXMLLoader(classLoader);
        SceneLoader.scene = new Scene(fxmlLoader.getRoot(WindowNames.START_WINDOW));
    }

    public static void load(String FXMLName) throws IOException {
        Parent root = fxmlLoader.getRoot(FXMLName);
        scene.setRoot(root);
    }

    public static Scene getScene() {
        return scene;
    }
}
