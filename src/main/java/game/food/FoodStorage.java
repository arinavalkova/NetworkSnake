package game.food;

import game.field.Cell;
import game.field.CellRole;
import game.field.GameField;

import java.util.ArrayList;

public class FoodStorage {
    private final int foodCount;
    private final GameField gameField;
    private final ArrayList<Cell> foodController;

    public FoodStorage(int foodCount, GameField gameField) {
        this.foodCount = foodCount;
        this.gameField = gameField;
        this.foodController = new ArrayList<>();
        generateFood();
    }

    private void generateFood() {
        int i = 0;
        int difference = foodCount - foodController.size();
        while (i < difference) {
            Cell generatedFoodInit = generateFoodInit();
            if (generatedFoodInit.findRole(CellRole.FOOD)) {
                continue;
            }
            addFood(generatedFoodInit);
            i++;
        }
    }

    private void addFood(Cell cell) {
        foodController.add(cell);
        cell.setRole(CellRole.FOOD);
    }

    public boolean findAndDelete(Cell foodUnit) {
        if (!foodUnit.findRole(CellRole.FOOD)) {
            return false;
        }
        delete(foodUnit);
        generateFood();
        return true;
    }

    private void delete(Cell foodUnit) {
        foodUnit.removeRole(CellRole.FOOD);
        foodController.remove(foodUnit);
    }

    private Cell generateFoodInit() {
        int x = randomInBounds(0, gameField.getWidth() - 1);
        int y = randomInBounds(0, gameField.getHeight() - 1);
        return gameField.getCell(x, y);
    }

    private int randomInBounds(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
}
