package game.multi.food;

import game.multi.field.Cell;
import game.multi.field.CellRole;
import game.multi.field.GameField;
import main.Random;

import java.util.ArrayList;

public class FoodStorage {
    private final int foodStatic;
    private final int foodPerPlayer;
    private final GameField gameField;
    private final ArrayList<Cell> foodController;


    public FoodStorage(int foodStatic, int foodPerPlayer, GameField gameField) {
        this.foodStatic = foodStatic;
        this.foodPerPlayer = foodPerPlayer;
        this.gameField = gameField;
        this.foodController = new ArrayList<>();
        generateFood();
    }

    private void generateFood() {
        int i = 0;
        int difference = /* FORMULA */ foodStatic - foodController.size();
        while (i < difference) {
            Cell generatedFoodInit = generateFoodInit();
            if (generatedFoodInit.findRole(CellRole.FOOD) ||
                    generatedFoodInit.findRole(CellRole.SNAKE)) continue;
            if (gameField.getSnakeCellsCount() + gameField.getFoodCellsCount() + 1
                    == gameField.getHeight() * gameField.getWidth()) break;
            addFood(generatedFoodInit);
            i++;
        }
    }

    private void addFood(Cell cell) {
        foodController.add(cell);
        cell.setRole(CellRole.FOOD);
        gameField.incFoodCellsCount();
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
        gameField.decFoodCellsCount();
    }

    private Cell generateFoodInit() {
        Random random = new Random();
        int x = random.inBounds(0, gameField.getWidth() - 1);
        int y = random.inBounds(0, gameField.getHeight() - 1);
        return gameField.getCell(x, y);
    }
}