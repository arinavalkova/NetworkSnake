package graphics.controllers.key.handlers;

import dto.Direction;
import game.multi.GamePlay;
import game.multi.Server;
import game.multi.proto.creators.SteerMessageCreator;
import game.multi.proto.viewers.GamePlayersViewer;
import game.multi.proto.viewers.SnakeViewer;
import graphics.controllers.key.KeyController;
import javafx.scene.input.KeyCode;

public class NormalKeyHandler implements KeyHandler{
    @Override
    public void handle(KeyController keyController, KeyCode keyCode) {
        GamePlay gamePlay = keyController.getGamePlay();
        Direction gottenDirection = keyController.getAllowedKey(keyCode);
        if (gottenDirection == null) {
            return;
        }
        gamePlay.getGameFieldDrawer().redrawField(gamePlay);
        Server.getNetwork().sendToSocket(
                new SteerMessageCreator(
                        gamePlay.getAndIncMsgSeq(),
                        gamePlay.getMy_id(),
                        gottenDirection
                ).getBytes(),
                new GamePlayersViewer(gamePlay.getGameState())
                        .getMasterAddress()
        );
        gamePlay.getGameFieldDrawer().redrawField(gamePlay);
    }
}
