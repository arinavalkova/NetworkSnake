package game.multi;

import dto.NodeRole;
import graphics.controllers.GameWindowController;
import graphics.data.NewGameWindowData;
import graphics.loaders.SceneController;

public class DecorateGame {
    private final GameWindowController gameWindowController;
    private final Network network;

    public DecorateGame(GameWindowController gameWindowController, Network network) {
        this.network = network;
        this.gameWindowController = gameWindowController;
    }

    public void decorateNewGame() {
        //another decorations
        gameWindowController.setFieldSize(NewGameWindowData.getFieldWidth(),
                NewGameWindowData.getFieldHeight());
        Game game = new Game(
                SceneController.getKeyController(),
                gameWindowController,
                network,
                NodeRole.MASTER,
                NewGameWindowData.getFieldWidth(),
                NewGameWindowData.getFieldHeight(),
                NewGameWindowData.getFoodStatic(),
                NewGameWindowData.getFoodPerPlayer(),
                NewGameWindowData.getStateDelay(),
                NewGameWindowData.getDeadFoodProb(),
                NewGameWindowData.getPingDelay(),
                NewGameWindowData.getNodeTimeOut()
        );
        game.start();
    }

    public void decorateJoinGameAs(NodeRole nodeRole) {
        //getting messages from MASTER for join
        //some preparations for join game window from this message
//        Game game = new Game(
//                gameWindowController,
//                network,
//                nodeRole, /* another params from message */
//        );
//        game.start();
    }
}
