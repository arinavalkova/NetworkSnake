package main;

import com.google.protobuf.InvalidProtocolBufferException;
import dto.*;
import dto.GameState;
import game.multi.proto.decorators.SnakeDecorator;

public class GenerateStateMsg {

    private GameState gameState;
    private String line;
    /** Генерирует пример сообщения с состоянием, соответствующим картинке example1.png */
    public void testGenerateStateMsg() throws InvalidProtocolBufferException {
        GameConfig config = GameConfig.newBuilder()
                .setWidth(10)
                .setHeight(10)
                // Все остальные параметры имеют значения по умолчанию
                .build();
        GameState.Snake snake = GameState.Snake.newBuilder()
                .setPlayerId(10)
                .setHeadDirection(Direction.LEFT)
                .setState(GameState.Snake.SnakeState.ALIVE)
                .addPoints(coord(5, 1)) // голова
                .addPoints(coord(5, 2))
                .addPoints(coord(5, 3))
                .addPoints(coord(5, 4))
                .build();
        // Единственный игрок в игре, он же MASTER
        GamePlayer playerBob = GamePlayer.newBuilder()
                .setId(1)
                .setRole(NodeRole.MASTER)
                .setIpAddress("") // MASTER не отправляет собственный IP
                .setPort(20101)
                .setName("Bob")
                .setScore(8)
                .build();
        GamePlayers players = GamePlayers.newBuilder()
                .addPlayers(playerBob)
                .build();
        GameState state = GameState.newBuilder()
                .setStateOrder(193)
                .addSnakes(snake)
                .setPlayers(players)
                .setConfig(config)
                .addFoods(coord(7, 6))
                .addFoods(coord(8, 7))
                .build();
        GameMessage.StateMsg stateMsg = GameMessage.StateMsg.newBuilder()
                .setState(state)
                .build();
        GameMessage gameMessage = GameMessage.newBuilder()
                .setMsgSeq(15643)
                .setState(stateMsg)
                .build();

        byte[] bytesToSendViaDatagramPacket = gameMessage.toByteArray();
//        GameMessage receivedGameMessage = GameMessage.parseFrom(bytesToSendViaDatagramPacket);
//        this.gameState = receivedGameMessage.getState().getState();
//        GameState.Snake snake3 = moveSnake(snake2, coord(5, 0));
//        this.gameState = GameState.newBuilder(this.gameState)



//        GameState.Snake snake3 = moveSnake(snake2, coord(5, 0));
//        List<GameState.Snake> oldSnakes = GameMessage.newBuilder(this.gameMessage)
//                .getState()
//                .getState()
//                .getSnakesList();
//        List<GameState.Snake> newSnakes = new ArrayList<>();
//        newSnakes.add(snake3);
//
//        GameState state2 = GameMessage.newBuilder(this.gameMessage)
//                .getState()
//                .getState()
//                .toBuilder()
//                .clearSnakes()
//                .addAllSnakes(newSnakes).build();
//        this.gameMessage = GameMessage.newBuilder(this.gameMessage)
//                .setState(state)
//        System.out.println(this.gameMessage.getState().getState().getSnakes(0).getPointsList());

        SnakeDecorator snakeDecorator = new SnakeDecorator(state);
        snakeDecorator.updateSnakeDirectionByPlayerId(10, Direction.DOWN);
        System.out.println(snakeDecorator.getSnakeDirectionByPlayerId(10));
        this.gameState = snakeDecorator.getGameState();
        System.out.println(this.gameState.getSnakesList());
    }

    private GameState.Coord coord(int x, int y) {
        return GameState.Coord.newBuilder().setX(x).setY(y).build();
    }
}
