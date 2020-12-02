package game.multi.snake.mover;

import dto.Direction;
import game.multi.field.Cell;
import game.multi.field.CellRole;
import game.multi.field.GameField;
import game.multi.food.FoodStorage;
import game.multi.snake.Snake;
import graphics.controllers.KeyController;

public class SnakeMover {
    private final KeyController keyController;
    private final FoodStorage foodStorage;
    private final Snake snake;
    private final GameField gameField;
    private int points;

    public SnakeMover(GameField gameField, KeyController keyController,
                      Snake snake, FoodStorage foodStorage) {
        this.gameField = gameField;
        this.keyController = keyController;
        this.foodStorage = foodStorage;
        this.snake = snake;
        points = 0;
    }

    public int start() {
//        Cell cell = move(snake.getHead());
//        if (cell.findRole(CellRole.SNAKE)) return -1;
//        if (foodStorage.findAndDelete(cell)) {
//            snake.eat(cell);
//            points++;
//        } else {
//            snake.move(cell);
//        }
//        return points;
        //получаем все сообщения steer message
        //получить свое направление с keyController
        //продвигаем зомби
        //выполняем каждое из них, запоминая вернувшиеся клетки для проверки и id_player змейки
        //проверяем: если клетка совпадает с клеткой еды ->> убираем  клетку еды, делаем на id_player eat() и генерим еду
        //           если клетка совпадает с какой-нибудь клеткой другой змеи из той же map, то оба эти id считаем умершими
        //                удаляем змеек и генерим еду с них, если это та же змейка удаляем ее и генерим еду
        //           если клетка ни с чем из вышеперечисленного не совпадает, то просто сделать move()
        //подумать над синхронизацией

        return 0;
    }
}
