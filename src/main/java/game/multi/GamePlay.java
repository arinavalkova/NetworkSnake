package game.multi;

import dto.GameState;
import dto.NodeRole;
import game.multi.players.*;
import game.multi.proto.viewers.GamePlayersViewer;
import game.multi.sender.milticast.SenderMulticast;
import graphics.controllers.GameWindowController;
import graphics.controllers.KeyController;
import graphics.drawers.GameFieldDrawer;
import main.TimeOut;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;

public class GamePlay implements ActionListener {
    private final Integer GAME_INFO_DRAWER_TIME_OUT = 5000;
    private GameWindowController gameWindowController;
    private final GameFieldDrawer gameFieldDrawer;
    private final Timer mainTimer;
    private final KeyController keyController;
    //private final ProtoParser protoParser;

    private GameState gameState;

    private NodeRole nodeRole;

    private int msg_seq;
    private final Object msq_seq_mutex;
    private final Object issued_id_mutex;
    private int issued_id;

    private int my_id;
    private int master_id;
    private int deputy_id;
    private InetSocketAddress masterSocketAddress;

    private boolean gameInfoDrawerWork = true;
    private final SenderMulticast senderMulticast;
    private boolean gameOver = false;

    public GamePlay(KeyController keyController
            , GameWindowController gameWindowController
            , GameState gameState
            , NodeRole nodeRole
            , int id
            , InetSocketAddress masterSocketAddress
    ) {
        this.masterSocketAddress = masterSocketAddress;
        this.senderMulticast = new SenderMulticast(Server.getNetwork());
        this.my_id = id;
        this.issued_id = 0;
        this.gameState = gameState;
        this.gameWindowController = gameWindowController;
        this.gameFieldDrawer = new GameFieldDrawer(
                gameWindowController.getCanvas().getGraphicsContext2D(),
                this
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
        this.nodeRole = nodeRole;
        msq_seq_mutex = new Object();
        issued_id_mutex = new Object();
    }

    private final Thread gameInfoDrawerThread = new Thread(() -> {
        while (gameInfoDrawerWork) {
            gameWindowController.setPlayersList(new GamePlayersViewer(gameState).getPlayersListToDraw());
            new TimeOut(GAME_INFO_DRAWER_TIME_OUT).start();
        }
    });

    public void start() {
        gameInfoDrawerThread.start();
        gameFieldDrawer.drawField(gameState);
        mainTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            stop();
        }
        playersMap.get(nodeRole).play(this);
        getGameFieldDrawer().redrawField(this);
    }

    public void stop() {
        Server.deleteFromCurrentGames(gameState);
        senderMulticast.stop();
        gameInfoDrawerWork = false;
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
        synchronized (msq_seq_mutex) {
            msg_seq++;
        }
        return msg_seq;
    }

    public int getAndIncIssuedId() {
        synchronized (issued_id_mutex) {
            issued_id++;
        }
        return issued_id;
    }

    public SocketAddress getMasterSocketAddress() {
        return masterSocketAddress;
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
        synchronized (this.gameState) {
            this.gameState = gameState;
        }
    }

    public SenderMulticast getSenderMulticast() {
        return senderMulticast;
    }
}
