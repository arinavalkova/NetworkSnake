package graphics.controllers.key.handlers;

import dto.Direction;
import game.multi.GamePlay;
import game.multi.proto.renovators.SnakeRenovator;
import game.multi.proto.viewers.SnakeViewer;
import graphics.controllers.key.KeyController;
import javafx.scene.input.KeyCode;

public class MasterKeyHandler implements KeyHandler {
    @Override
    public void handle(KeyController keyController, KeyCode keyCode) {
        GamePlay gamePlay = keyController.getGamePlay();
        Direction gottenDirection = keyController.getAllowedKey(keyCode);
        if (gottenDirection == null) {
            return;
        }
        new SnakeRenovator(gamePlay).updateSnakeDirectionByPlayerId(gamePlay.getMy_id(), gottenDirection);
    }
}
