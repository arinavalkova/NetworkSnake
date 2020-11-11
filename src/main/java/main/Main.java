package main;

import javafx.application.Application;
import javafx.stage.Stage;
import graphics.loaders.SceneLoader;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        SceneLoader.loadMain(classLoader);
        primaryStage.setScene(SceneLoader.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
//        SnakeField snakeField = new SnakeField();
//        Scene scene = new Scene(snakeField, Invariables.GAME_WIDTH, Invariables.GAME_HEIGHT);
//        snakeField.drawField();
    }
}
