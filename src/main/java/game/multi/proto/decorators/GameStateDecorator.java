package game.multi.proto.decorators;

import dto.GameConfig;
import dto.GameState;

public class GameStateDecorator {
    private GameState gameState;

    public GameStateDecorator(GameState gameState) {
        this.gameState = gameState;
    }

    //метод для удаления змеи
    //метод для обновления змеи по индексу
    //метод для добавления новой змеи
    //что будет если удалить что-то нулевое, как измениться count, и можно ли добавить в начало?
}
