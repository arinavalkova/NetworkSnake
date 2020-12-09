package graphics.controllers.key;

import dto.Direction;
import dto.NodeRole;
import game.multi.GamePlay;
import game.multi.Server;
import game.multi.proto.creators.SteerMessageCreator;
import game.multi.proto.renovators.SnakeRenovator;
import game.multi.proto.viewers.GamePlayersViewer;
import game.multi.proto.viewers.SnakeViewer;
import game.multi.receive.handlers.*;
import graphics.controllers.key.handlers.*;
import graphics.loaders.SceneController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class KeyController {
    private final GamePlay gamePlay;

    public KeyController(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    public void setKeyPressed(KeyCode keyCode) {
        NodeRole nodeRole = gamePlay.getNodeRole();
        keyHandlerMap.get(nodeRole).handle(this, keyCode);
    }

    public Direction getAllowedKey(KeyCode keyCode) {
        Direction supposedDirection = null;
        Direction gottenDirection = null;
        if (keyCode == KeyCode.W) {
            supposedDirection = Direction.UP;
        } else if (keyCode == KeyCode.S) {
            supposedDirection = Direction.DOWN;
        } else if (keyCode == KeyCode.A) {
            supposedDirection = Direction.LEFT;
        } else if (keyCode == KeyCode.D) {
            supposedDirection = Direction.RIGHT;
        }

        Direction currentDirection = new SnakeViewer(gamePlay.getGameState())
                .getSnakeDirection(gamePlay.getMy_id());

        if (supposedDirection == Direction.DOWN && currentDirection != Direction.UP) {
            gottenDirection = supposedDirection;
        } else if (supposedDirection == Direction.UP && currentDirection != Direction.DOWN) {
            gottenDirection = supposedDirection;
        } else if (supposedDirection == Direction.LEFT && currentDirection != Direction.RIGHT) {
            gottenDirection = supposedDirection;
        } else if (supposedDirection == Direction.RIGHT && currentDirection != Direction.LEFT) {
            gottenDirection = supposedDirection;
        }

        if (gottenDirection == currentDirection) {
            return null;
        }
        return gottenDirection;
    }

    public void start() {
        SceneController.getScene().setOnKeyPressed(event -> {
            setKeyPressed(event.getCode());
        });
    }

    private static final Map<NodeRole, KeyHandler> keyHandlerMap = Map.of(
            NodeRole.MASTER, new MasterKeyHandler(),
            NodeRole.NORMAL, new NormalKeyHandler(),
            NodeRole.VIEWER, new ViewerKeyHandler(),
            NodeRole.DEPUTY, new DeputyKeyHandler()
    );

    public GamePlay getGamePlay() {
        return gamePlay;
    }
}
