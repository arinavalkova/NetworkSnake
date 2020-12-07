package graphics.controllers;

import dto.Direction;
import dto.NodeRole;
import game.multi.GamePlay;
import game.multi.Server;
import game.multi.proto.creators.SteerMessageCreator;
import game.multi.proto.renovators.SnakeRenovator;
import game.multi.proto.viewers.GamePlayersViewer;
import game.multi.proto.viewers.SnakeViewer;
import graphics.loaders.SceneController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.event.ActionListener;

public class KeyController {
    private final GamePlay gamePlay;

    public KeyController(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    public void setKeyPressed(KeyCode keyCode) {
        Direction supposedKey = null;
        if (keyCode == KeyCode.W) {
            supposedKey = Direction.UP;
        } else if (keyCode == KeyCode.S) {
            supposedKey = Direction.DOWN;
        } else if (keyCode == KeyCode.A) {
            supposedKey = Direction.LEFT;
        } else if (keyCode == KeyCode.D) {
            supposedKey = Direction.RIGHT;
        }
        Direction currentDirection = new SnakeViewer(gamePlay.getGameState())
                .getSnakeDirection(gamePlay.getMy_id());
        Direction gottenDirection = getAllowedKey(supposedKey, currentDirection);
        if (gottenDirection == currentDirection) {
            return;
        }
        if (gamePlay.getNodeRole() == NodeRole.MASTER) {
            new SnakeRenovator(gamePlay).updateSnakeDirectionByPlayerId(gamePlay.getMy_id(), gottenDirection);
        } else {
            Server.getNetwork().sendToSocket(
                    new SteerMessageCreator(
                            gamePlay.getAndIncMsgSeq(),
                            gamePlay.getMy_id(),
                            gottenDirection
                    ).getBytes(),
                    new GamePlayersViewer(gamePlay.getGameState())
                            .getMasterAddress()
            );
            System.out.println("sent");
        }
    }

    public Direction getAllowedKey(Direction supposedDirection, Direction currentDirection) {
        if (supposedDirection == Direction.DOWN && currentDirection != Direction.UP) {
            currentDirection = supposedDirection;
        } else if (supposedDirection == Direction.UP && currentDirection != Direction.DOWN) {
            currentDirection = supposedDirection;
        } else if (supposedDirection == Direction.LEFT && currentDirection != Direction.RIGHT) {
            currentDirection = supposedDirection;
        } else if (supposedDirection == Direction.RIGHT && currentDirection != Direction.LEFT) {
            currentDirection = supposedDirection;
        }
        return currentDirection;
    }

    public void start() {
        SceneController.getScene().setOnKeyPressed(event -> {
            setKeyPressed(event.getCode());
        });
    }
}
