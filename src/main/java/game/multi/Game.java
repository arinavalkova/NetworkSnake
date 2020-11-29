package game.multi;

import dto.GameConfig;
import dto.GamePlayer;
import dto.GameState;
import dto.NodeRole;
import game.multi.field.CellRole;
import game.multi.field.GameField;
import game.multi.food.FoodStorage;
import game.multi.players.*;
import game.multi.proto.parsers.ProtoParser;
import game.multi.sender.milticast.ConfirmSender;
import game.multi.snake.Snake;
import game.multi.snake.mover.SnakeMover;
import graphics.controllers.GameWindowController;
import graphics.controllers.KeyController;
import graphics.drawers.GameFieldDrawer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketAddress;
import java.util.Map;

public class Game implements ActionListener {
    private final GameWindowController gameWindowController;
    private final GameFieldDrawer gameFieldDrawer;
    private final Network network;
    private final SnakeMover snakeMover;
    private final Timer mainTimer;
    private final KeyController keyController;
    private final ConfirmSender confirmSender;
    //private final ProtoParser protoParser;

    private GameConfig gameConfig;
    private GamePlayer localGamePlayer;


    private NodeRole nodeRole;

    private int msg_seq;
    private int issued_id;

    private int my_id;
    private int master_id;
    private int deputy_id;

    private boolean gameOver = false;

    public Game(
            KeyController keyController,
            GameWindowController gameWindowController,
            Network network,
            GameStateDecorator gameStateDecorator) {
        this.issued_id = 0;
        this.gameWindowController = gameWindowController;
        this.gameConfig = gameState.getConfig();
        this.network = network;
        GameField gameField = new GameField(gameConfig.getWidth(), gameConfig.getHeight());
        this.gameFieldDrawer = new GameFieldDrawer(
                gameWindowController.getCanvas().getGraphicsContext2D(),
                gameField
        );
        Snake snake = new Snake(gameField);
        FoodStorage foodStorage = new FoodStorage(
                gameConfig.getFoodStatic(),
                gameConfig.getFoodPerPlayer(),
                gameField
        );
        snakeMover = new SnakeMover(gameField, keyController, snake, foodStorage);
        this.mainTimer = new Timer(gameConfig.getStateDelayMs(), this);
        this.keyController = keyController;
        this.confirmSender = new ConfirmSender(
                gameConfig.getPingDelayMs(),
                gameConfig.getNodeTimeoutMs(),
                network,
                this
        );
        //this.protoParser = new ProtoParser();
        this.msg_seq = 0;
        this.localGamePlayer = newGamePlayer;
        //grab master info
    }

    public void start() {
        gameFieldDrawer.drawField();
        mainTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            stop();
        }
        playersMap.get(nodeRole).play(this);
        gameWindowController.updatePlayersList(); // <-- give here ArrayList of GamePlayerDecorator's
        getGameFieldDrawer().redrawField();
        getGameFieldDrawer().draw(CellRole.FOOD);
        getGameFieldDrawer().draw(CellRole.SNAKE);
    }

    public void stop() {
        gameFieldDrawer.drawEndOfGame();
        //maybe something else for end of game
        //stop receiver unicast
    }

    public GameFieldDrawer getGameFieldDrawer() {
        return gameFieldDrawer;
    }

    public SnakeMover getSnakeMover() {
        return snakeMover;
    }

    public GameWindowController getGameWindowController() {
        return gameWindowController;
    }

    public Network getNetwork() {
        return network;
    }

    public NodeRole getNodeRole() {
        return nodeRole;
    }

    private static final Map<NodeRole, Player> playersMap = Map.of(
            NodeRole.DEPUTY, new DeputyPlayer(),
            NodeRole.MASTER, new MasterPlayer(),
            NodeRole.NORMAL, new NormalPlayer(),
            NodeRole.VIEWER, new ViewerPlayer()
    );

    public KeyController getKeyController() {
        return keyController;
    }

    public void setNodeRole(NodeRole nodeRole) {
        this.nodeRole = nodeRole;
    }

    public void setGameOver(boolean state) {
        gameOver = state;
    }

    public ConfirmSender getConfirmSender() {
        return confirmSender;
    }

//    public ProtoParser getProtoParser() {
//        return protoParser;
//    }

    public int getAndIncMsgSeq() {
        return msg_seq++;
    }

    public int getAndIncIssuedId() {
        return issued_id++;
    }

    public SocketAddress getMasterSocketAddress() {
        return null;
    }

    public int getMy_id() {
        return my_id;
    }

    public int getMaster_id() {
        return master_id;
    }

    public int getDeputy_id() {
        return deputy_id;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public void setGameConfig(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }
}
