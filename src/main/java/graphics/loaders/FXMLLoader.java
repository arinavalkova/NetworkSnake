package graphics.loaders;

import javafx.scene.Parent;

import java.io.IOException;

public class FXMLLoader {
    private Parent root;
    private final ClassLoader classLoader;

    public FXMLLoader(ClassLoader classLoader) {
       this.classLoader = classLoader;
    }

    public Parent getRoot(String FXMLName) {
        try {
            root = javafx.fxml.FXMLLoader.load(classLoader.getResource(FXMLName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}
