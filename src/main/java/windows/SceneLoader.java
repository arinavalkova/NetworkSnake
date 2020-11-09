package windows;

import javafx.scene.Parent;
import javafx.scene.Scene;
import main.Invariables;

public class SceneLoader {
    private static Scene scene;
    private static ClassLoader classLoader;
    private static FXMLLoader fxmlLoader;

    public static void loadMain(ClassLoader classLoader) {
        SceneLoader.classLoader = classLoader;
        fxmlLoader = new FXMLLoader(classLoader);
        SceneLoader.scene = new Scene(fxmlLoader.getRoot(Invariables.START_WINDOW));
    }

    public static void load(String FXMLName) {
        Parent root = fxmlLoader.getRoot(FXMLName);
        scene.setRoot(root);
    }

    public static Scene getScene() {
        return scene;
    }
}
