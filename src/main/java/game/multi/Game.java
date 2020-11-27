package game.multi;

import dto.NodeRole;
import game.multi.field.CellRole;
import game.multi.field.GameField;
import game.multi.food.FoodStorage;
import game.multi.players.*;
import game.multi.sender.milticast.ConfirmSender;
import game.multi.snake.Snake;
import game.multi.snake.mover.SnakeMover;
import graphics.controllers.GameWindowController;
import graphics.controllers.KeyController;
import graphics.drawers.GameFieldDrawer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Game implements ActionListener {
    private final GameWindowController gameWindowController;
    private final GameFieldDrawer gameFieldDrawer;
    private final int stateDelay;
    private NodeRole nodeRole;
    private final Network network;
    private final int pingDelay;
    private final int nodeTimeOut;
    private final double deadFoodProb;
    private final SnakeMover snakeMover;
    private final Timer mainTimer;
    private final KeyController keyController;
    private final ConfirmSender confirmSender;

    private boolean gameOver = false;

    //master info

    public Game(
            KeyController keyController,
            GameWindowController gameWindowController,
            Network network,
            NodeRole nodeRole,
            int fieldWidth,
            int fieldHeight,
            int foodStatic,
            int foodPerPlayer,
            int stateDelay,
            double deadFoodProb,
            int pingDelay,
            int nodeTimeOut
    ) {
        this.gameWindowController = gameWindowController;
        GameField gameField = new GameField(fieldWidth, fieldHeight);
        this.gameFieldDrawer = new GameFieldDrawer(
                gameWindowController.getCanvas().getGraphicsContext2D(),
                gameField
        );
        this.stateDelay = stateDelay;
        this.nodeRole = nodeRole;
        this.network = network;
        this.pingDelay = pingDelay;
        this.nodeTimeOut = nodeTimeOut;
        this.deadFoodProb = deadFoodProb;
        Snake snake = new Snake(gameField);
        FoodStorage foodStorage = new FoodStorage(foodStatic, foodPerPlayer, gameField);
        snakeMover = new SnakeMover(gameField, keyController, snake, foodStorage);
        this.mainTimer = new Timer(stateDelay, this);
        this.keyController = keyController;
        this.confirmSender = new ConfirmSender(pingDelay, nodeTimeOut, network);
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
        //getGameWindowController().setPoints(points);
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
}
