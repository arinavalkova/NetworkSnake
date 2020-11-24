package main;

import graphics.loaders.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        SceneController.loadMain(classLoader);
        primaryStage.setScene(SceneController.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
