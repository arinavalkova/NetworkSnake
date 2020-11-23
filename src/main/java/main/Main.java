package main;

import game.multi.ServerGame;
import javafx.application.Application;
import javafx.stage.Stage;
import graphics.loaders.SceneController;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ServerGame.startServer();

        ClassLoader classLoader = getClass().getClassLoader();
        SceneController.loadMain(classLoader);
        primaryStage.setScene(SceneController.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
//        SnakeField snakeField = new SnakeField();
//        Scene scene = new Scene(snakeField, Invariables.GAME_WIDTH, Invariables.GAME_HEIGHT);
//        snakeField.drawField();
    }
}
