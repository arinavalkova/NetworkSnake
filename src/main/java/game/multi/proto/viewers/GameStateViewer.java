package game.multi.proto.viewers;

import dto.Direction;
import dto.GameState;
import main.Random;

import java.util.ArrayList;
import java.util.List;

public class GameStateViewer {
    private final static int NEIGHBOUR_COUNT = 2;
    private GameState gameState;

    public GameStateViewer(GameState gameState) {
        this.gameState = gameState;
    }

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

    public List<GameState.Coord> getAllSnakesCoords() {
        List<GameState.Coord> allSnakesCoords = new ArrayList<>();
        for (int i = 0; i < gameState.getSnakesCount(); i++) {
            allSnakesCoords.addAll(gameState.getSnakes(i).getPointsList());
        }
        return allSnakesCoords;
    }

    public List<GameState.Coord> getAllFoodsCoords() {
        return gameState.getFoodsList();
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

    public List<GameState.Coord> findCoordsForNewSnake() {
        List<GameState.Coord> emptyCoords = new GameStateViewer(gameState).getAllEmptyCoords();
        List<GameState.Coord> answerList = new ArrayList<>();
        while (!emptyCoords.isEmpty()) {
            GameState.Coord currentCoord = emptyCoords.get(new Random().inBoundsInt(0, emptyCoords.size() - 1));
            emptyCoords.remove(currentCoord);
            if (isCellNeighborhoodEmpty(currentCoord)) {
                answerList.add(currentCoord);
                answerList.add(new SnakeViewer(gameState).checkOutOfBoundary(currentCoord.getX() + 1,
                        currentCoord.getY(), gameState));
                break;
            }
        }
        return answerList;
    }

    public void addSnake(int playerId) {
        List<GameState.Coord> startCoords = findCoordsForNewSnake();
//        if (startCoords.isEmpty())
//            return null;
        GameState.Snake newSnake = GameState.Snake.newBuilder()
                .setHeadDirection(Direction.UP)
                .setState(GameState.Snake.SnakeState.ALIVE)
                .setPlayerId(playerId)
                .addAllPoints(startCoords)
                .build();
        gameState = GameState.newBuilder(gameState)
                .addSnakes(newSnake)
                .build();
    }

    private boolean isCellNeighborhoodEmpty(GameState.Coord currentCoord) {
        int coordX = currentCoord.getX();
        int coordY = currentCoord.getY();

        int fieldWidth = gameState.getConfig().getWidth();
        int fieldHeight = gameState.getConfig().getHeight();

        int xStart = coordX - NEIGHBOUR_COUNT < 0 ?
                fieldWidth - (NEIGHBOUR_COUNT - coordX)
                :
                coordX - NEIGHBOUR_COUNT;
        int yStart = coordY - NEIGHBOUR_COUNT < 0 ?
                fieldHeight - (NEIGHBOUR_COUNT - coordY)
                :
                coordY - NEIGHBOUR_COUNT;
        for (int i = xStart, count_i = 0; count_i < NEIGHBOUR_COUNT * NEIGHBOUR_COUNT + 1;
             count_i++, i = ((i + 1) == fieldWidth ? 0 : (i + 1))
        ) {
            for (int j = yStart, count_j = 0; count_j < NEIGHBOUR_COUNT * NEIGHBOUR_COUNT + 1;
                 count_j++, j = ((j + 1) == fieldHeight ? 0 : (j + 1))
            ) {
                if (!isCellEmpty(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isCellEmpty(int i, int j) {
        List<GameState.Coord> emptyCoords = new GameStateViewer(gameState).getAllEmptyCoords();
        for (GameState.Coord currentCoord : emptyCoords) {
            if (currentCoord.getX() == i && currentCoord.getY() == j) {
                return true;
            }
        }
        return false;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void generateFoodIfNecessary() {
        int neededCount = (int) (gameState.getConfig().getFoodStatic() +
                gameState.getConfig().getFoodPerPlayer() *
                        new GameStateViewer(gameState).getAliveSnakesCount()) - gameState.getFoodsCount();
        if (neededCount == 0) {
            return;
        }
        Random random = new Random();
        List<GameState.Coord> emptyCoords = new GameStateViewer(gameState).getAllEmptyCoords();
        for(int i = 0; i < neededCount; i++) {
            GameState.Coord randomCoord = emptyCoords.get(random.inBoundsInt(0, emptyCoords.size() - 1));
            gameState = GameState.newBuilder(gameState)
                    .addFoods(randomCoord)
                    .build();
            emptyCoords.remove(randomCoord);
        }
    }

    public List<GameState.Snake> getAllSnakes() {
        List<GameState.Snake> allSnakes = new ArrayList<>();
        for (int i = 0; i < gameState.getSnakesCount(); i++) {
            allSnakes.add(gameState.getSnakes(i));
        }
        return allSnakes;
    }
}
