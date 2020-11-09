package windows;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import main.Invariables;

public class SnakeCanvas {
    private final Canvas canvas;
    public SnakeCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void draw() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Paint.valueOf(Invariables.FIELD_COLOR));
        graphicsContext.fillRect(0, 0, Invariables.SNAKE_FIELD_WIDTH, Invariables.SNAKE_FIELD_HEIGHT);
    }
}
