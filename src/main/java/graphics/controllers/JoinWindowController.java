package graphics.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import dto.NodeRole;
import game.multi.Server;
import graphics.data.JoinGameWindowData;
import graphics.loaders.SceneController;
import graphics.loaders.WindowNames;
import javafx.application.Platform;
import javafx.fxml.FXML;
import main.TimeOut;

import java.io.IOException;
import java.net.InetSocketAddress;

public class JoinWindowController {

    private final static int GAMES_LIST_TIME_OUT = 10000;

//    @FXML
//    private Label errorField;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton joinButton;

    @FXML
    private JFXCheckBox viewerModeCheckBox;

    @FXML
    private JFXListView<String> gamesListView;


    @FXML
    void initialize() {
        updateGamesList();
        addButtonsHandlers();
    }

    private void updateGamesList() {
        Thread updateGameListThread = new Thread(() -> {
            while (!Server.isStopped()) {
                    Platform.runLater(() -> {
                        gamesListView.getItems().clear();
                        gamesListView.getItems().addAll(Server
                                .getReceiverMulticast()
                                .getCurrentGames()
                                .getCurrentGames()
                        );
                    });
                    new TimeOut(GAMES_LIST_TIME_OUT).start();
            }
        });
        updateGameListThread.start();
    }

    private void addButtonsHandlers() {
        addBackButtonHandler();
        addJoinButtonHandler();
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

    private void addJoinButtonHandler() {
        joinButton.setOnAction(e -> {
           Thread joinWork = new Thread(() -> {
               if (!loadDataFromWindow()) {
                   return;
               }

               InetSocketAddress socketAddress = getCurrentGameAddress();
               if (socketAddress == null) {
                   return;
               }

               String serverAnswer = Server.joinGameIfSuccess(viewerModeCheckBox.isSelected() ?
                               NodeRole.VIEWER
                               :
                               NodeRole.NORMAL,
                       socketAddress,
                       JoinGameWindowData.getName());
               if (serverAnswer != null) {
                   //errorField.setText(serverAnswer);
               }
           });
           joinWork.start();
        });
    }

    private InetSocketAddress getCurrentGameAddress() {
        InetSocketAddress inetSocketAddress = null;
        String currentGameLine = gamesListView.getSelectionModel().getSelectedItem();
        if (currentGameLine == null) {
            return null;
        }
        while (inetSocketAddress == null) {
            inetSocketAddress = Server
                    .getReceiverMulticast()
                    .getCurrentGames()
                    .findAddressByStringLine(currentGameLine);
        }
        return inetSocketAddress;
    }

    private boolean loadDataFromWindow() {
        if (nameField.getText().isEmpty()) {
            return false;
        }
        JoinGameWindowData.setName(nameField.getText());
        return true;
    }
}
