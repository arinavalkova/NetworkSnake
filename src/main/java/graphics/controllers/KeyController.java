package graphics.controllers;

import game.single.snake.mover.MoveDirection;
import javafx.scene.input.KeyCode;

public class KeyController{
    private MoveDirection key = MoveDirection.UP;
    private MoveDirection supposedKey = MoveDirection.UP;

    public MoveDirection getKey() {
        return setKeyIfAllowed(supposedKey);
    }

    public void setKeyPressed(KeyCode key) {
        System.out.println("Pressed: " + key);
        if (key == KeyCode.W) {
            supposedKey = MoveDirection.UP;
        } else if (key == KeyCode.S) {
            supposedKey = MoveDirection.DOWN;
        } else if (key == KeyCode.A) {
            supposedKey = MoveDirection.LEFT;
        } else if (key == KeyCode.D) {
            supposedKey = MoveDirection.RIGHT;
        }
    }

    public MoveDirection setKeyIfAllowed(MoveDirection supposedDirection) {
        if (supposedDirection == MoveDirection.DOWN && key != MoveDirection.UP) {
            key = supposedDirection;
        } else if (supposedDirection == MoveDirection.UP && key != MoveDirection.DOWN) {
            key = supposedDirection;
        } else if (supposedDirection == MoveDirection.LEFT && key != MoveDirection.RIGHT) {
            key = supposedDirection;
        } else if (supposedDirection == MoveDirection.RIGHT && key != MoveDirection.LEFT) {
            key = supposedDirection;
        }
        return key;
    }
}
