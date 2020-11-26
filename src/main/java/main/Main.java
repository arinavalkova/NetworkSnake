package main;

import game.multi.Server;
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
        Server.start();
        ClassLoader classLoader = getClass().getClassLoader();
        SceneController.loadMain(classLoader);
        primaryStage.setScene(SceneController.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            Server.stop();
            primaryStage.close();
        });
    }
}
