package game.multi;

import dto.NodeRole;
import game.multi.field.GameField;
import game.multi.food.FoodStorage;
import game.multi.players.*;
import game.multi.receive.unicast.UnicastMessagesStorage;
import game.multi.sender.milticast.SenderMulticast;
import game.multi.snake.Snake;
import game.multi.snake.mover.SnakeMover;
import graphics.controllers.GameWindowController;
import graphics.controllers.KeyController;
import graphics.drawers.GameFieldDrawer;
import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Game implements ActionListener {
    private final UnicastMessagesStorage unicastMessagesStorage;
    private final GameWindowController gameWindowController;
    private final GameFieldDrawer gameFieldDrawer;
    private final int stateDelay;
    private NodeRole nodeRole;
    private final Network network;
    private final int pingDelay;
    private final int nodeTimeOut;
    private final double deadFoodProb;
    private final SnakeMover snakeMover;
    private final Timer timer;
    private final SenderMulticast senderMulticast;
    private final KeyController keyController;

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
        this.timer = new Timer(stateDelay, this);
        this.unicastMessagesStorage = new UnicastMessagesStorage(network);
        this.senderMulticast = new SenderMulticast(network);
        this.keyController = keyController;
    }

    public void start() {
        unicastMessagesStorage.start();
        gameFieldDrawer.drawField();
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (playersMap.get(nodeRole).play(this) == -1) {
            end();
        }
    }

    private void end() {
        gameFieldDrawer.drawEndOfGame();
        //maybe something else for end of game
        //stop receiver unicast
        timer.stop();
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

    public UnicastMessagesStorage getReceiverUnicast() {
        return unicastMessagesStorage;
    }

    private static final Map<NodeRole, Player> playersMap = Map.of(
            NodeRole.DEPUTY, new DeputyPlayer(),
            NodeRole.MASTER, new MasterPlayer(),
            NodeRole.NORMAL, new NormalPlayer(),
            NodeRole.VIEWER, new ViewerPlayer()
    );

    public SenderMulticast getSenderMulticast() {
        return senderMulticast;
    }

    public KeyController getKeyController() {
        return keyController;
    }
}
