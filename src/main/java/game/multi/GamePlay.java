package game.multi;

import dto.GameState;
import dto.NodeRole;
import game.multi.players.*;
import graphics.controllers.GameWindowController;
import graphics.controllers.KeyController;
import graphics.drawers.GameFieldDrawer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketAddress;
import java.util.Map;

public class GamePlay implements ActionListener {
    private final GameWindowController gameWindowController;
    private final GameFieldDrawer gameFieldDrawer;
    private final Timer mainTimer;
    private final KeyController keyController;
    //private final ProtoParser protoParser;

    private GameState gameState;

    private NodeRole nodeRole;

    private int msg_seq;
    private int issued_id;

    private int my_id;
    private int master_id;
    private int deputy_id;

    private boolean gameOver = false;

    public GamePlay(KeyController keyController
            , GameWindowController gameWindowController
            , GameState gameState, int id) {
        this.my_id = id;
        this.issued_id = 0;
        this.gameState = gameState;
        this.gameWindowController = gameWindowController;
        this.gameFieldDrawer = new GameFieldDrawer(
                gameWindowController.getCanvas().getGraphicsContext2D(),
                gameState
        );
        this.mainTimer = new Timer(gameState.getConfig().getStateDelayMs(), this);
        this.keyController = keyController;
//        this.confirmSender = new ConfirmSender(
//                gameConfig.getPingDelayMs(),
//                gameConfig.getNodeTimeoutMs(),
//                network,
//                this
//        );
        this.msg_seq = 1;
        //grab master info
        //установить тут NODE_ROLE
        this.nodeRole = NodeRole.MASTER;
    }

    public void start() {
        gameFieldDrawer.drawField(gameState);
        mainTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            stop();
        }
        playersMap.get(nodeRole).play(this);
        getGameFieldDrawer().redrawField(gameState);
    }

    public void stop() {
        gameFieldDrawer.drawEndOfGame(gameState);
        mainTimer.stop();
        //maybe something else for end of game
        //stop receiver unicast
    }

    public GameFieldDrawer getGameFieldDrawer() {
        return gameFieldDrawer;
    }

    public GameWindowController getGameWindowController() {
        return gameWindowController;
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

//    public ConfirmSender getConfirmSender() {
//        return confirmSender;
//    }

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

    public GameState getGameState() {
        return gameState;
    }

    public void updateGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
