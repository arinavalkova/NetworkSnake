package game.multi.proto.decorators;

import dto.GameState;
import main.Random;

import java.util.ArrayList;
import java.util.List;

public class GameStateDecorator {
    private GameState gameState;

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
        Random random = new Random();
        List<GameState.Coord> emptyCoords = getAllEmptyCoords();
        for(int i = 0; i < neededCount; i++) {
            GameState.Coord randomCoord = emptyCoords.get(random.inBounds(0, emptyCoords.size() - 1));
            gameState = GameState.newBuilder(gameState)
                    .addFoods(randomCoord)
                    .build();
            emptyCoords.remove(randomCoord);
        }
    }

    public boolean isCellEmpty(int i, int j) {
        List<GameState.Coord> emptyCoords = getAllEmptyCoords();
        for (GameState.Coord currentCoord : emptyCoords) {
            if (currentCoord.getX() == i && currentCoord.getY() == j) {
                return true;
            }
        }
        return false;
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

    public GameState getGameState() {
        return gameState;
    }
}
