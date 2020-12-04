package game.multi.players;

import dto.Direction;
import dto.GameState;
import dto.NodeRole;
import game.multi.GamePlay;
import game.multi.Server;
import game.multi.proto.creators.AnnouncmentMessageCreator;
import game.multi.proto.creators.StateMessageCreator;
import game.multi.proto.renovators.GamePlayerRenovator;
import game.multi.proto.renovators.GameStateRenovator;
import game.multi.proto.renovators.SnakeRenovator;
import game.multi.proto.viewers.GamePlayersViewer;
import game.multi.proto.viewers.SnakeViewer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MasterPlayer implements Player {
    @Override
    public void play(GamePlay gamePlay) {
        GameState gameState = gamePlay.getGameState();
        if (gamePlay.getGameWindowController().getButtonNodeRole() == NodeRole.VIEWER) {
            //send deputy that he is master now
            //send deputy also all raw messages
            //send deputy that my snake is ZOMBIE
            //stop all master sendings
            gamePlay.setNodeRole(NodeRole.VIEWER);
            return;
        }
        //получаем все сообщения steer message
        //получить свое направление с keyController
        //продвигаем зомби
        //выполняем каждое из них, запоминая вернувшиеся клетки для проверки и id_player змейки
        //проверяем: если клетка совпадает с клеткой еды ->> убираем  клетку еды, делаем на id_player eat() и генерим еду
        //           если клетка совпадает с какой-нибудь клеткой другой змеи из той же map, то оба эти id считаем умершими
        //                удаляем змеек и генерим еду с них, если это та же змейка удаляем ее и генерим еду
        //           если клетка ни с чем из вышеперечисленного не совпадает, то просто сделать move()
        //подумать над синхронизацией

        //у нас в итоге все двигаются
        //нужна функцию куда предать мой айди, и steer сообщения и пусть вернется мапа с айди и координатой


        Server.getGamePlay().getSenderMulticast().updateGameStateInvite(
                new AnnouncmentMessageCreator(
                        gamePlay.getAndIncMsgSeq()
                        , gameState
                ).getBytes()
        );     /* Invite send thread updating */


        Direction directionFromKeyController = gamePlay.getKeyController().getKey();
        new SnakeRenovator(gamePlay)
                .updateSnakeDirectionByPlayerId(gamePlay.getMy_id(), directionFromKeyController);

        Map<Integer, GameState.Coord> testMoveCoords = testMoveAllPlayers(gamePlay);
        if (!checkAllPlayers(testMoveCoords, gamePlay)) {
            //MASTER is gone
            gamePlay.setGameOver(true);
        }
        Server.getNetwork().sendToListOfAddresses(
                new GamePlayersViewer(gameState).getSocketAddressOfAllPlayersWithOutMaster(),
                new StateMessageCreator(gamePlay.getAndIncMsgSeq(), gameState).getBytes()
        );
    }

    private boolean checkAllPlayers(Map<Integer, GameState.Coord> testMoveMap, GamePlay gamePlay) {
        boolean iAlive = true;
        List<GameState.Coord> foodsCoords = gamePlay.getGameState().getFoodsList();
        while (!testMoveMap.isEmpty()) {
            Iterator<Map.Entry<Integer, GameState.Coord>> entries = testMoveMap.entrySet().iterator();
            Map.Entry<Integer, GameState.Coord> entry = entries.next();

            GameState.Coord coordIfItFood = findCoordInListOfCoords(entry.getValue(), foodsCoords);
            Map<Integer, GameState.Coord> coordsIfItSnakes = findSameCoordsInMap(entry.getKey(),
                    entry.getValue(), testMoveMap);

            if (coordIfItFood != null) {
                new GameStateRenovator(gamePlay).deleteFood(coordIfItFood);
                new SnakeRenovator(gamePlay).snakeEat(entry.getKey());
                new GamePlayerRenovator(gamePlay).incPoints(entry.getKey());
            } else if (!coordsIfItSnakes.isEmpty()) {
                for (Map.Entry<Integer, GameState.Coord> playersForDelete : coordsIfItSnakes.entrySet()) {
                    //say all coords if it snakes that they have game over
                    // если кто-то из них мастер то iAlive = false;
                    testMoveMap.remove(playersForDelete.getKey());
                }
            } else if (findCoordInListOfCoords(entry.getValue(),
                    new SnakeViewer(gamePlay.getGameState()).getSnakeCoords(entry.getKey())) != null) {
                if (entry.getKey() == gamePlay.getMy_id()) {
                    iAlive = false;
                    //Master has game over
                }
                //say that he has game over
            } else {
                new SnakeRenovator(gamePlay).snakeMove(entry.getKey());
            }
            testMoveMap.remove(entry.getKey());
        }
        new GameStateRenovator(gamePlay).generateFoodIfNecessary();
        return iAlive;
    }

    private Map<Integer, GameState.Coord> findSameCoordsInMap(int player_id,
                                                      GameState.Coord coord,
                                                      Map<Integer, GameState.Coord> mapOfCoords
    ) {
        Map<Integer, GameState.Coord> map = new HashMap<>();
        for (Map.Entry<Integer, GameState.Coord> entry : mapOfCoords.entrySet()) {
            if (entry.getValue().getX() == coord.getX() && entry.getValue().getY() == coord.getY() &&
            entry.getKey() != player_id) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

    private List<GameState.Coord> getValuesListFromMap(Map<Integer, GameState.Coord> map) {
        List<GameState.Coord> list = new ArrayList<>();
        for (Map.Entry<Integer, GameState.Coord> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    private GameState.Coord findCoordInListOfCoords(GameState.Coord coord, List<GameState.Coord> coordList) {
        for (GameState.Coord currentCoord : coordList) {
            if (currentCoord.getX() == coord.getX() && currentCoord.getY() == coord.getY()) {
                return currentCoord;
            }
        }
        return null;
    }

    private Map<Integer, GameState.Coord> testMoveAllPlayers(GamePlay gamePlay) {
        Map<Integer, GameState.Coord> testMoveMap = new ConcurrentHashMap<>();
        List<GameState.Snake> snakesList = gamePlay.getGameState().getSnakesList();
        for (int i = 0; i < snakesList.size(); i++) {
            testMoveMap.put(
                    snakesList.get(i).getPlayerId(),
                    new SnakeViewer(gamePlay.getGameState()).testSnakeMoveBySnakeId(i)
            );
        }
        return testMoveMap;
    }

    private boolean checkIfSnakeHasBadMove(GameState.Coord moveMeCoord, int my_id, GamePlay gamePlay) {
        List<GameState.Coord> snakeCoords = gamePlay.getGameState().getSnakes(my_id).getPointsList();
        List<GameState.Coord> foodsCoords = gamePlay.getGameState().getFoodsList();
        //тут все неправильно с id и надо нормальные методы 
        for (GameState.Coord currentCoord : snakeCoords) {
            if (currentCoord.getX() == moveMeCoord.getX()
                    && currentCoord.getY() == moveMeCoord.getY()) {
                return true;//тут нужны какие то изменения в proto змеи
            }
        }
        for (GameState.Coord currentCoord : foodsCoords) {
            if (currentCoord.getX() == moveMeCoord.getX()
                    && currentCoord.getY() == moveMeCoord.getY()) {
                new SnakeRenovator(gamePlay).snakeEat(my_id);
                return false;
            }
        }

        new SnakeRenovator(gamePlay).snakeMove(my_id);
        return false;
    }
}