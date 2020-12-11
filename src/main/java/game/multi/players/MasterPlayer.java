package game.multi.players;

import dto.Direction;
import dto.GameMessage;
import dto.GameState;
import dto.NodeRole;
import game.multi.GamePlay;
import game.multi.Server;
import game.multi.proto.creators.AckMessageCreator;
import game.multi.proto.creators.AnnouncmentMessageCreator;
import game.multi.proto.creators.RoleChangeMessageCreator;
import game.multi.proto.creators.StateMessageCreator;
import game.multi.proto.renovators.GamePlayerRenovator;
import game.multi.proto.renovators.GamePlayersRenovator;
import game.multi.proto.renovators.GameStateRenovator;
import game.multi.proto.renovators.SnakeRenovator;
import game.multi.proto.viewers.GamePlayerViewer;
import game.multi.proto.viewers.GamePlayersViewer;
import game.multi.proto.viewers.GameStateViewer;
import game.multi.proto.viewers.SnakeViewer;
import game.multi.stoppers.MasterToViewer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MasterPlayer {
    public boolean play(GamePlay gamePlay) {
        GameState gameState = gamePlay.getGameState();

        Server.getGamePlay().getSenderMulticast().updateGameStateInvite(
                new AnnouncmentMessageCreator(
                        gamePlay.getAndIncMsgSeq()
                        , gameState
                ).getBytes()
        );

        applySteerMessages(gamePlay);

        Map<Integer, GameState.Coord> testMoveCoords = testMoveAllPlayers(gamePlay);
        checkAllPlayers(testMoveCoords, gamePlay);
        return true;
    }

    private void applySteerMessages(GamePlay gamePlay) {
        List<GameMessage> steerMessages = gamePlay.getSteerMessages();
        for (GameMessage currentGameMessage : steerMessages) {
            SnakeRenovator snakeRenovator = new SnakeRenovator(gamePlay);
            snakeRenovator.updateSnakeDirectionByPlayerId(
                    currentGameMessage.getSenderId(),
                    currentGameMessage.getSteer().getDirection()
            );
        }
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
            GameState.Snake.SnakeState snakeState = new SnakeViewer(gamePlay.getGameState())
                    .getSnakeStateByPlayerId(entry.getKey());
            if (coordIfItFood != null) {
                new GameStateRenovator(gamePlay).deleteFood(coordIfItFood);
                new SnakeRenovator(gamePlay).snakeEat(entry.getKey());
                if (snakeState != GameState.Snake.SnakeState.ZOMBIE) {
                    new GamePlayerRenovator(gamePlay).incPoints(entry.getKey());
                }
            } else if (!coordsIfItSnakes.isEmpty()) {
                for (Map.Entry<Integer, GameState.Coord> playersForDelete : coordsIfItSnakes.entrySet()) {
                    if (playersForDelete.getKey() == gamePlay.getMy_id()) {
                        new SnakeRenovator(gamePlay).deleteSnake(entry.getKey());
                        Server.getNetwork().sendToListOfAddresses(
                                new GamePlayersViewer(gamePlay.getGameState()).getSocketAddressOfAllPlayersWithOutMaster(),
                                new StateMessageCreator(gamePlay.getAndIncMsgSeq(), gamePlay.getGameState()).getBytes()
                        );
                        new MasterToViewer().start(gamePlay);
                        Server.getNetwork().sendToSocket(
                                new RoleChangeMessageCreator(
                                        gamePlay.getAndIncMsgSeq(),
                                        gamePlay.getMy_id(),
                                        null,
                                        NodeRole.VIEWER,
                                        null
                                ).getBytes(),
                                gamePlay.getDeputySocketAddress()
                        );
                        return iAlive;
                    } else if (snakeState != GameState.Snake.SnakeState.ZOMBIE) {
                        GameStateRenovator gameStateRenovator = new GameStateRenovator(gamePlay);
                        int newPlayerId = gameStateRenovator.addViewerPlayer(
                                new GamePlayerViewer(gamePlay.getGameState()).getPlayerSocketAddress(entry.getKey()),
                                new GamePlayerViewer(gamePlay.getGameState()).getPlayerName(playersForDelete.getKey()));
                        gamePlay.updateGameState(gameStateRenovator.getGameState());
                        GamePlayersRenovator gamePlayersRenovator = new GamePlayersRenovator(gamePlay);
                        gamePlayersRenovator.deletePlayer(playersForDelete.getKey());
                        gamePlay.updateGameState(gamePlayersRenovator.getGameState());
                        Server.getNetwork().sendToSocket(
                                new AckMessageCreator(
                                        gamePlay.getAndIncMsgSeq(),
                                        gamePlay.getMy_id(),
                                        newPlayerId).getBytes(),
                                new GamePlayerViewer(gamePlay.getGameState()).getPlayerSocketAddress(entry.getKey())
                        );
                        Server.getNetwork().sendToSocket(
                                new RoleChangeMessageCreator(
                                        gamePlay.getAndIncMsgSeq(),
                                        gamePlay.getMy_id(),
                                        null,
                                        null,
                                        NodeRole.VIEWER).getBytes(),
                                new GamePlayerViewer(gamePlay.getGameState())
                                        .getPlayerSocketAddress(newPlayerId)
                        );
                        new SnakeRenovator(gamePlay).deleteSnake(playersForDelete.getKey());
                    }
                    testMoveMap.remove(playersForDelete.getKey());
                }
            } else if (findCoordInListOfCoords(entry.getValue(),
                    new GameStateViewer(gamePlay.getGameState()).getAllSnakesCoords()) != null) {
                if (entry.getKey() == gamePlay.getMy_id()) {
                    new SnakeRenovator(gamePlay).deleteSnake(entry.getKey());
                    Server.getNetwork().sendToListOfAddresses(
                            new GamePlayersViewer(gamePlay.getGameState()).getSocketAddressOfAllPlayersWithOutMaster(),
                            new StateMessageCreator(gamePlay.getAndIncMsgSeq(), gamePlay.getGameState()).getBytes()
                    );
                    new MasterToViewer().start(gamePlay);
                    Server.getNetwork().sendToSocket(
                            new RoleChangeMessageCreator(
                                    gamePlay.getAndIncMsgSeq(),
                                    gamePlay.getMy_id(),
                                    null,
                                    NodeRole.VIEWER,
                                    null
                            ).getBytes(),
                            gamePlay.getDeputySocketAddress()
                    );
                    return iAlive;
                } else if (snakeState != GameState.Snake.SnakeState.ZOMBIE) {
                    GameStateRenovator gameStateRenovator = new GameStateRenovator(gamePlay);
                    int newPlayerId = gameStateRenovator.addViewerPlayer(
                            new GamePlayerViewer(gamePlay.getGameState()).getPlayerSocketAddress(entry.getKey()),
                            new GamePlayerViewer(gamePlay.getGameState()).getPlayerName(entry.getKey()));
                    gamePlay.updateGameState(gameStateRenovator.getGameState());
                    GamePlayersRenovator gamePlayersRenovator = new GamePlayersRenovator(gamePlay);
                    gamePlayersRenovator.deletePlayer(entry.getKey());
                    gamePlay.updateGameState(gamePlayersRenovator.getGameState());
                    Server.getNetwork().sendToSocket(
                            new AckMessageCreator(
                                    gamePlay.getAndIncMsgSeq(),
                                    gamePlay.getMy_id(),
                                    newPlayerId).getBytes(),
                            new GamePlayerViewer(gamePlay.getGameState()).getPlayerSocketAddress(newPlayerId)
                    );
                    Server.getNetwork().sendToSocket(
                            new RoleChangeMessageCreator(
                                    gamePlay.getAndIncMsgSeq(),
                                    gamePlay.getMy_id(),
                                    null,
                                    null,
                                    NodeRole.VIEWER).getBytes(),
                            new GamePlayerViewer(gamePlay.getGameState()).getPlayerSocketAddress(newPlayerId)
                    );
                    new SnakeRenovator(gamePlay).deleteSnake(entry.getKey());
                }
            } else {
                new SnakeRenovator(gamePlay).snakeMove(entry.getKey());
            }
            testMoveMap.remove(entry.getKey());
        }
        gamePlay.updateDeputy();
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
}