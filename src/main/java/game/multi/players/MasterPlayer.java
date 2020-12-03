package game.multi.players;

import dto.Direction;
import dto.GameState;
import dto.NodeRole;
import game.multi.GamePlay;
import game.multi.Server;
import game.multi.proto.creators.AnnouncmentMessageCreator;
import game.multi.proto.decorators.GameStateDecorator;
import game.multi.proto.decorators.SnakeDecorator;

import java.nio.file.DirectoryStream;
import java.util.List;
import java.util.Map;

public class MasterPlayer implements Player {
    @Override
    public void play(GamePlay gamePlay) {
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


        Server.getSenderMulticast().updateGameStateInvite(
                new AnnouncmentMessageCreator(
                        gamePlay.getAndIncMsgSeq()
                        , gamePlay.getGameState()
                ).getBytes()
        );     /* Invite send thread updating */


        Direction directionFromKeyController = gamePlay.getKeyController().getKey();
        SnakeDecorator snakeDecorator = new SnakeDecorator(gamePlay.getGameState());
        snakeDecorator.updateSnakeDirectionByPlayerId(gamePlay.getMy_id(), directionFromKeyController);
        gamePlay.updateGameState(snakeDecorator.getGameState());
        GameState.Coord moveMeCoord = snakeDecorator.testSnakeMove(gamePlay.getMy_id());
        //тут проверка всей мапы, на пересечения, тут же если что удаления, отсылки сообщений получается
        if (checkIfSnakeHasBadMove(moveMeCoord, gamePlay.getMy_id(), gamePlay)) {
            gamePlay.setGameOver(true);
        }

//        int points;//play check all which uplyed by receiver
//        if ((points = gamePlay.getSnakeMover().start()) == -1) {
//            Server.getSenderMulticast().stop();
//            //send deputy that he is master now
//            //send deputy also all raw messages
//            //send deputy that my snake is ZOMBIE
//            //stop all master sendings
//            gamePlay.setGameOver(true);
//        }
        //send to all players GameState
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
                SnakeDecorator snakeDecorator = new SnakeDecorator(gamePlay.getGameState());
                snakeDecorator.snakeEat(my_id);
                gamePlay.updateGameState(snakeDecorator.getGameState());
                return false;
            }
        }

        SnakeDecorator snakeDecorator = new SnakeDecorator(gamePlay.getGameState());
        snakeDecorator.snakeMove(my_id);
        gamePlay.updateGameState(snakeDecorator.getGameState());
        return false;
    }
}