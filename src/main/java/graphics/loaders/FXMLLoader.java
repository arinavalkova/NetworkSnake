package graphics.loaders;

import javafx.scene.Parent;

import java.io.IOException;

public class FXMLLoader {
    private final ClassLoader classLoader;

    public FXMLLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Parent getRoot(String FXMLName) throws IOException {
        return javafx.fxml.FXMLLoader.load(classLoader.getResource(FXMLName));
    }
}
