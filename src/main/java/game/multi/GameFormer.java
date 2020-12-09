package game.multi;

import dto.*;
import game.multi.proto.renovators.GameStateRenovator;
import game.multi.proto.viewers.GameStateViewer;
import graphics.controllers.GameWindowController;
import graphics.data.JoinGameWindowData;
import graphics.data.NewGameWindowData;
import graphics.loaders.SceneController;

import java.net.InetSocketAddress;

public class GameFormer {
    private final static String EMPTY_LINE = "";
    private final static int NEW_PLAYER_ID = 0;
    private final static int NEW_GAME_STATE_NUM = 0;

    private final GameWindowController gameWindowController;
    private final Network network;
    private GamePlay gamePlay;

    public GameFormer(GameWindowController gameWindowController, Network network) {
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
                network,
                gameWindowController,
                newGameGameState,
                NodeRole.MASTER,
                0,
                null
        );
        gamePlay.start();
    }

    private GameState createNewGameState() {
        GamePlayers gamePlayers = GamePlayers.newBuilder()
                .addPlayers(createNewGamePlayer())
                .build();
        GameState gameState = GameState.newBuilder()
                .setStateOrder(NEW_GAME_STATE_NUM)
                .setPlayers(gamePlayers)
                .setConfig(createNewGameConfig())
                .build();

        GameStateViewer gameStateViewer = new GameStateViewer(gameState);
        gameStateViewer.addSnake(NEW_PLAYER_ID);
        gameStateViewer.generateFoodIfNecessary();
        return gameStateViewer.getGameState();
    }

    public void decorateJoinGameAs(GameState gameState
            , NodeRole nodeRole
            , int my_id
            , InetSocketAddress masterSocketAddress) {
        gameWindowController.setFoodField(gameState.getConfig().getFoodStatic(),
                gameState.getConfig().getFoodPerPlayer());
        gameWindowController.setFieldSize(gameState.getConfig().getWidth(),
                gameState.getConfig().getHeight());
        this.gamePlay = new GamePlay(
                network,
                gameWindowController,
                gameState,
                nodeRole,
                my_id,
                masterSocketAddress
        );
        gamePlay.start();
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
