package game.food;

import game.Cell;

import java.util.ArrayList;

public class FoodStorage {
    private final int FOOD_COUNT;
    private final int fieldWidth;
    private final int fieldHeight;
    private final ArrayList<Cell> foodStorage;

    public FoodStorage(int foodCount, int fieldWidth, int fieldHeight) {
        this.FOOD_COUNT = foodCount;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.foodStorage = new ArrayList<>();
        generateIfNecessary();
    }

    private void generateIfNecessary() {
        int differance = FOOD_COUNT - foodStorage.size();
        for (int i = 0; i < differance; i++) {
            foodStorage.add(generateFoodInit());
        }
    }

    public boolean findAndDelete(Cell foodUnit) {
        Cell foodInStorage = find(foodUnit);
        if (foodInStorage == null) {
            return false;
        }
        delete(foodInStorage);
        generateIfNecessary();
        return true;
    }

    private void delete(Cell foodInStorage) {
        foodStorage.remove(foodInStorage);
    }

    private Cell find(Cell foodUnit) {
        for (Cell currentFoodInit : foodStorage) {
            if (currentFoodInit.equals(foodUnit)) {
                return  currentFoodInit;
            }
        }
        return null;
    }

    private Cell generateFoodInit() {
        int x = randomInBounds(0, fieldWidth - 1);
        int y = randomInBounds(0, fieldHeight - 1);
        return new Cell(x, y);
    }

    private int randomInBounds(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    public ArrayList<Cell> getCells() {
        return foodStorage;
    }
}
