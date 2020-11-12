package graphics.controllers;

import game.snake.mover.MoveDirection;
import javafx.scene.input.KeyCode;

public class KeyController{
    private MoveDirection key = MoveDirection.UP;

    public MoveDirection getKey() {
        return key;
    }

    public void setKeyPressed(KeyCode key) {
        if (key == KeyCode.W) {
            setKeyIfAllowed(MoveDirection.UP);
        } else if (key == KeyCode.S) {
            setKeyIfAllowed(MoveDirection.DOWN);
        } else if (key == KeyCode.A) {
            setKeyIfAllowed(MoveDirection.LEFT);
        } else if (key == KeyCode.D) {
            setKeyIfAllowed(MoveDirection.RIGHT);
        }
    }

    private void setKeyIfAllowed(MoveDirection supposedDirection) {
        if (supposedDirection == MoveDirection.DOWN && key != MoveDirection.UP) {
            key = supposedDirection;
        } else if (supposedDirection == MoveDirection.UP && key != MoveDirection.DOWN) {
            key = supposedDirection;
        } else if (supposedDirection == MoveDirection.LEFT && key != MoveDirection.RIGHT) {
            key = supposedDirection;
        } else if (supposedDirection == MoveDirection.RIGHT && key != MoveDirection.LEFT) {
            key = supposedDirection;
        }
    }
}
