package game.single.field;

import main.Random;

import java.util.ArrayList;

public class GameField {
    private final int FREE_AREA = 5;
    private final ArrayList<Cell> gameField;
    private final int width;
    private final int height;
    private int foodCellsCount;
    private int snakeCellsCount;

    public GameField(int width, int height) {
        this.gameField = new ArrayList<>();
        this.height = height;
        this.width = width;
        foodCellsCount = 0;
        snakeCellsCount = 0;
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
            Random random = new Random();
            x = random.inBounds(FREE_AREA - 1, width - FREE_AREA + 1);
            y = random.inBounds(FREE_AREA - 1, height - FREE_AREA + 1);
            Cell cell = getCell(x, y);
            if (cell.findRole(CellRole.SNAKE)) {
                continue;
            }
            return cell;
        }
    }

    public int getFoodCellsCount() {
        return foodCellsCount;
    }

    public int getSnakeCellsCount() {
        return snakeCellsCount;
    }

    public void incFoodCellsCount() {
        foodCellsCount++;
    }

    public void incSnakeCellsCount() {
        snakeCellsCount++;
    }

    public void decFoodCellsCount() {
        foodCellsCount--;
    }

    public void decSnakeCellsCount() {
        snakeCellsCount--;
    }
}
