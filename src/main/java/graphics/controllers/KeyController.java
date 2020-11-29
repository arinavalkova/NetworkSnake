package graphics.controllers;

import dto.Direction;
import javafx.scene.input.KeyCode;

public class KeyController{
    private Direction key = Direction.UP;
    private Direction supposedKey = Direction.UP;

    public Direction getKey() {
        return setKeyIfAllowed(supposedKey);
    }

    public void setKeyPressed(KeyCode key) {
        System.out.println("Pressed: " + key);
        if (key == KeyCode.W) {
            supposedKey = Direction.UP;
        } else if (key == KeyCode.S) {
            supposedKey = Direction.DOWN;
        } else if (key == KeyCode.A) {
            supposedKey = Direction.LEFT;
        } else if (key == KeyCode.D) {
            supposedKey = Direction.RIGHT;
        }
    }

    public Direction setKeyIfAllowed(Direction supposedDirection) {
        if (supposedDirection == Direction.DOWN && key != Direction.UP) {
            key = supposedDirection;
        } else if (supposedDirection == Direction.UP && key != Direction.DOWN) {
            key = supposedDirection;
        } else if (supposedDirection == Direction.LEFT && key != Direction.RIGHT) {
            key = supposedDirection;
        } else if (supposedDirection == Direction.RIGHT && key != Direction.LEFT) {
            key = supposedDirection;
        }
        return key;
    }
}
