package game.multi.proto.decorators;

import dto.GameState;

import java.util.ArrayList;
import java.util.List;

public class GameStateDecorator {
    private final GameState gameState;

    public GameStateDecorator(GameState gameState) {
        this.gameState = gameState;
    }

    //получение мапы клеток еды и player_id

    //получение списка клеток змеек и player_id


    public int getAliveSnakesCount() {
        int count = 0;
        List<GameState.Snake> snakes = gameState.getSnakesList();
        for (GameState.Snake currentSnake : snakes) {
            if (currentSnake.getState() == GameState.Snake.SnakeState.ALIVE) {
                count++;
            }
        }
        return count;
    }

    public void generateFoodIfNecessary() {
        int neededCount = (int) (gameState.getConfig().getFoodStatic() +
                gameState.getConfig().getFoodPerPlayer() * getAliveSnakesCount()) - gameState.getFoodsCount();
        if (neededCount == 0) {
            return;
        }
        List<GameState.Coord> allEmptyCoords = getAllEmptyCoords();
        for (int i = 0; i < neededCount; i++) {

        }
    }

    private List<GameState.Coord> getAllFoodsCoords() {
        return gameState.getFoodsList();
    }

    private List<GameState.Coord> getAllSnakesCoords() {
        List<GameState.Coord> allSnakesCoords = new ArrayList<>();
        for (int i = 0; i < gameState.getSnakesCount(); i++) {
            allSnakesCoords.addAll(gameState.getSnakes(i).getPointsList());
        }
        return allSnakesCoords;
    }

    public List<GameState.Coord> getAllEmptyCoords() {
        List<GameState.Coord> coords = getAllFieldCoords();
        List<GameState.Coord> allSnakesCoords = getAllSnakesCoords();
        List<GameState.Coord> allFoodsCoords = getAllFoodsCoords();
        coords.removeAll(allSnakesCoords);
        coords.removeAll(allFoodsCoords);
        return coords;
    }

    private List<GameState.Coord> getAllFieldCoords() {
        int fieldWidth = gameState.getConfig().getWidth();
        int fieldHeight = gameState.getConfig().getHeight();
        List<GameState.Coord> allFieldCoordsList = new ArrayList<>();
        for (int i = 0; i < fieldWidth; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                allFieldCoordsList.add(GameState.Coord.newBuilder()
                        .setX(i)
                        .setY(j)
                        .build()
                );
            }
        }
        return allFieldCoordsList;
    }
}
