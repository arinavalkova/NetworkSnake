package graphics.loaders;

import javafx.fxml.FXMLLoader;

public class GameFXMLLoader {
    private final ClassLoader classLoader;

    public GameFXMLLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public FXMLLoader getFXMLoader(String FXMLName) {
        return new FXMLLoader(classLoader.getResource(FXMLName));
    }
}
