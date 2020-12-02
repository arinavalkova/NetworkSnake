package game.multi;

import dto.*;
import game.multi.proto.decorators.GameStateDecorator;
import graphics.controllers.GameWindowController;
import graphics.data.JoinGameWindowData;
import graphics.data.NewGameWindowData;
import graphics.loaders.SceneController;

public class GameDecorator {
    private final static String EMPTY_LINE = "";
    private final static int NEW_PLAYER_ID = 0;
    private final static int NEW_GAME_STATE_NUM = 0;

    private final GameWindowController gameWindowController;
    private final Network network;
    private GamePlay gamePlay;

    public GameDecorator(GameWindowController gameWindowController, Network network) {
        this.network = network;
        this.gameWindowController = gameWindowController;
    }

    public void decorateNewGame() {
        GameState newGameGameState = createNewGameState();
        gameWindowController.setFoodField(newGameGameState.getConfig().getFoodStatic(),
                newGameGameState.getConfig().getFoodPerPlayer());
        gameWindowController.setFieldSize(newGameGameState.getConfig().getWidth(),
                newGameGameState.getConfig().getHeight());

        this.gamePlay = new GamePlay(
                SceneController.getKeyController(),
                gameWindowController,
                newGameGameState,
                0
        );
        gamePlay.start();
    }

    private GameState createNewGameState() {
        GamePlayers gamePlayers = GamePlayers.newBuilder()
                .addPlayers(createNewGamePlayer())
                .build();
        GameState.Builder gameStateBuilder = GameState.newBuilder()
                .setStateOrder(NEW_GAME_STATE_NUM)
                .setPlayers(gamePlayers)
                .setConfig(createNewGameConfig());
        GameStateDecorator gameStateDecorator = new GameStateDecorator(gameStateBuilder.build());
        gameStateDecorator.addSnake(NEW_PLAYER_ID);
        gameStateDecorator.generateFoodIfNecessary();
        return gameStateDecorator.getGameState();
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

    public GamePlay getGame() {
        return gamePlay;
    }

    public GamePlayer createNewGamePlayer() {
        return GamePlayer.newBuilder()
                .setName(NewGameWindowData.getName())
                .setId(NEW_PLAYER_ID)
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
                .setWidth                        (   NewGameWindowData.getFieldWidth()    )
                .setHeight                       (   NewGameWindowData.getFieldHeight()   )
                .setFoodStatic                   (   NewGameWindowData.getFoodStatic()    )
                .setFoodPerPlayer                (   NewGameWindowData.getFoodPerPlayer() )
                .setStateDelayMs                 (   NewGameWindowData.getStateDelay()    )
                .setDeadFoodProb                 (   NewGameWindowData.getDeadFoodProb()  )
                .setPingDelayMs                  (   NewGameWindowData.getPingDelay()     )
                .setNodeTimeoutMs                (   NewGameWindowData.getNodeTimeOut()   )
                .build();
    }
}
