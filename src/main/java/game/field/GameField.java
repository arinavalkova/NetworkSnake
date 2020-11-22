package game.field;

import java.util.ArrayList;

public class GameField {
    private final int FREE_AREA = 5;
    private final ArrayList<Cell> gameField;
    private final int width;
    private final int height;

    public GameField(int width, int height) {
        this.gameField = new ArrayList<>();
        this.height = height;
        this.width = width;
        createField();
    }

    private void createField() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                gameField.add(new Cell(i, j));
            }
        }
    }

    public ArrayList<Cell> getGameField() {
        return gameField;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell getCell(int x, int y) {
        return gameField.get(x * height + y);
    }

    public Cell getCenterOfFreeArea() {
        int x, y;
        while (true) {
            x = randomInBounds(FREE_AREA - 1, width - FREE_AREA + 1);
            y = randomInBounds(FREE_AREA - 1, height - FREE_AREA + 1);
            Cell cell = getCell(x, y);
            if (cell.findRole(CellRole.SNAKE)) {
                continue;
            }
            return cell;
        }
    }

    private int randomInBounds(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
}
