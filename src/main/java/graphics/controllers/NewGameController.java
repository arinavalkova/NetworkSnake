package graphics.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import game.multi.Server;
import graphics.data.NewGameWindowData;
import graphics.loaders.SceneController;
import graphics.loaders.WindowNames;
import javafx.fxml.FXML;

import java.io.IOException;

public class NewGameController {
    @FXML
    private JFXSlider widthSlider;

    @FXML
    private JFXSlider heightSlider;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton playButton;

    @FXML
    private JFXSlider foodStaticSlider;

    @FXML
    private JFXSlider foodPerPlayerSlider;

    @FXML
    private JFXSlider stateDelaySlider;

    @FXML
    private JFXSlider deadFoodProbSlider;

    @FXML
    private JFXSlider pingDelaySlider;

    @FXML
    private JFXSlider nodeTimeOutSlider;

    @FXML
    void initialize() {
        addButtonsHandlers();
    }


    private void addButtonsHandlers() {
        addBackButtonHandler();
        addPlayButtonHandler();
    }

    private void addBackButtonHandler() {
        backButton.setOnAction(e -> {
            try {
                SceneController.load(WindowNames.START_WINDOW);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void addPlayButtonHandler() {
        playButton.setOnAction(e -> {
            /*        SINGLE PLAYER
             *
             *        SingleGame singleGame = new SingleGame(
             *                SceneController.load(
             *                          WindowNames.GAME_WINDOW
             *                 ).getController(),
             *                NewGameWindowData.getFieldWidth(),
             *                NewGameWindowData.getFieldHeight(),
             *                SceneController.getKeyController(),
             *                NewGameWindowData.getStateDelay(),
             *                NewGameWindowData.getFoodStatic()
             *        );
             *        singleGame.start();
             */
            loadDataFromWindow();
            try {
                Server.startNewGame(SceneController.load(
                                                  WindowNames.GAME_WINDOW
                                         ).getController());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void loadDataFromWindow() {
        NewGameWindowData.setFieldWidth        ((int) widthSlider.getValue()           );
        NewGameWindowData.setFieldHeight       ((int) heightSlider.getValue()          );
        NewGameWindowData.setDeadFoodProb      (      deadFoodProbSlider.getValue()    );
        NewGameWindowData.setFoodPerPlayer     ((int) foodPerPlayerSlider.getValue()   );
        NewGameWindowData.setFoodStatic        ((int) foodStaticSlider.getValue()      );
        NewGameWindowData.setNodeTimeOut       ((int) nodeTimeOutSlider.getValue()     );
        NewGameWindowData.setPingDelay         ((int) pingDelaySlider.getValue()       );
        NewGameWindowData.setStateDelay        ((int) stateDelaySlider.getValue()      );
    }
}
