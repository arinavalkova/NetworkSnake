package graphics.controllers.key.handlers;

import game.multi.GamePlay;
import graphics.controllers.key.KeyController;
import javafx.scene.input.KeyCode;

public interface KeyHandler {
    void handle(KeyController keyController, KeyCode keyCode);
}
