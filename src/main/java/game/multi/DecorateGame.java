package game.multi;

import dto.GameConfig;
import dto.GamePlayer;
import dto.NodeRole;
import dto.PlayerType;
import graphics.controllers.GameWindowController;
import graphics.data.JoinGameWindowData;
import graphics.data.NewGameWindowData;
import graphics.loaders.SceneController;

public class DecorateGame {
    private final static String EMPTY_LINE = "";

    private final GameWindowController gameWindowController;
    private final Network network;
    private Game game;

    public DecorateGame(GameWindowController gameWindowController, Network network) {
        this.network = network;
        this.gameWindowController = gameWindowController;
    }

    public void decorateNewGame() {
        GamePlayer newGamePlayer = createNewGamePlayer();

        GameConfig newGameConfig = createNewGameConfig();
        gameWindowController.setFoodField(newGameConfig.getFoodStatic(), newGameConfig.getFoodPerPlayer());
        gameWindowController.setFieldSize(newGameConfig.getWidth(), newGameConfig.getHeight());

        this.game = new Game(
                SceneController.getKeyController(),
                gameWindowController,
                network,
                newGameConfig,
                newGamePlayer
        );
        game.start();
    }

    public void decorateJoinGameAs(NodeRole nodeRole) {
        //send MASTER request to join as nodeRole
        //getting messages from MASTER for join
        //some preparations for join game window from this message
//        Game game = new Game(
//                gameWindowController,
//                network,
//                nodeRole, /* another params from message */
//        );
//        game.start();
    }

    public Game getGame() {
        return game;
    }

    public GamePlayer createNewGamePlayer() {
        return GamePlayer.newBuilder()
                .setName(NewGameWindowData.getName())
                .setId(0)
                .setIpAddress(EMPTY_LINE)
                .setPort(network.getUnicastSocketPort())
                .setRole(NodeRole.MASTER)
                .setType(PlayerType.HUMAN)
                .setScore(0)
                .build();
    }

    public GamePlayer createJoinGamePlayer(int issuedId) {
        return GamePlayer.newBuilder()
                .setName(JoinGameWindowData.getName())
                .setId(issuedId)
                .setIpAddress(EMPTY_LINE)
                .setPort(network.getUnicastSocketPort())
                .setRole(NodeRole.NORMAL)
                .setType(PlayerType.HUMAN)
                .setScore(0)
                .build();
    }

    public GameConfig createNewGameConfig() {
        return GameConfig.newBuilder()
                .setWidth(NewGameWindowData.getFieldWidth())
                .setHeight(NewGameWindowData.getFieldHeight())
                .setFoodStatic(NewGameWindowData.getFoodStatic())
                .setFoodPerPlayer(NewGameWindowData.getFoodPerPlayer())
                .setStateDelayMs(NewGameWindowData.getStateDelay())
                .setDeadFoodProb(NewGameWindowData.getDeadFoodProb())
                .setPingDelayMs(NewGameWindowData.getPingDelay())
                .setNodeTimeoutMs(NewGameWindowData.getNodeTimeOut())
                .build();
    }
}
