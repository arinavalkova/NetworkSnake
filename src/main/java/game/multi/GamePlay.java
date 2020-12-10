package game.multi;

import dto.GameMessage;
import dto.GameState;
import dto.NodeRole;
import game.multi.players.*;
import game.multi.proto.creators.JoinMessageCreator;
import game.multi.proto.creators.StateMessageCreator;
import game.multi.proto.renovators.GamePlayerRenovator;
import game.multi.proto.viewers.GamePlayerViewer;
import game.multi.proto.viewers.GamePlayersViewer;
import game.multi.receive.ReceiverUnicast;
import game.multi.sender.milticast.SenderMulticast;
import graphics.controllers.GameWindowController;
import graphics.controllers.key.KeyController;
import graphics.drawers.GameFieldDrawer;
import main.TimeOut;
import game.multi.stoppers.DeputyToViewer;
import game.multi.stoppers.MasterToViewer;
import game.multi.stoppers.NormalToViewer;
import game.multi.stoppers.ToViewer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.Map;

public class GamePlay implements ActionListener {
    private final Integer GAME_INFO_DRAWER_TIME_OUT = 5000;
    private final Integer CHANGE_MODE_TIME_OUT = 1000;

    private GameWindowController gameWindowController;
    private final GameFieldDrawer gameFieldDrawer;
    private final Timer masterTimer;
    private final KeyController keyController;

    private final ReceiverUnicast receiverUnicast;

    private GameState gameState;

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

    private final MasterPlayer masterPlayer;
    private final Thread modeButtonControllerThread;
    private boolean isModeButtonControllerThreadWork = true;

    public GamePlay(Network network
            , GameWindowController gameWindowController
            , GameState gameState
            , NodeRole nodeRole
            , int id
            , InetSocketAddress masterSocketAddress) {
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
        this.masterTimer = new Timer(gameState.getConfig().getStateDelayMs(), this);
        this.keyController = new KeyController(this);
        this.keyController.start();
//        this.confirmSender = new ConfirmSender(
//                gameConfig.getPingDelayMs(),
//                gameConfig.getNodeTimeoutMs(),
//                network,
//                this,
//                roleChanger
//        );
        this.msg_seq = 1;
        //grab master info
        msq_seq_mutex = new Object();
        issued_id_mutex = new Object();
        this.receiverUnicast = new ReceiverUnicast(network, this);
        masterPlayer = new MasterPlayer();

        this.modeButtonControllerThread = new Thread(() -> {
            while (isModeButtonControllerThreadWork) {
                NodeRole gottenNodeRole = gameWindowController.getButtonNodeRole();
                if (gottenNodeRole != null) {
                    if (gottenNodeRole == NodeRole.VIEWER) {
                        stoppersMap.get(getMyNodeRole()).start(this);
                    } else if (gottenNodeRole == NodeRole.NORMAL) {
                        network.sendToSocket(
                                new JoinMessageCreator(0,
                                        false,
                                        new GamePlayerViewer(this.gameState).getPlayerName(my_id),
                                        my_id)
                                        .getBytes(),
                                masterSocketAddress
                        );//to confirm
                    }
                    gameWindowController.changedRoleHandled();
                }
                new TimeOut(CHANGE_MODE_TIME_OUT).start();
            }
        });
    }

    private final Thread gameInfoDrawerThread = new Thread(() -> {
        while (gameInfoDrawerWork) {
            gameWindowController.setPlayersList(new GamePlayersViewer(gameState).getPlayersListToDraw());
            new TimeOut(GAME_INFO_DRAWER_TIME_OUT).start();
        }
    });

    public void start() {
        receiverUnicast.start();
        gameInfoDrawerThread.start();
        gameFieldDrawer.drawField(gameState);
        modeButtonControllerThread.start();
        if (new GamePlayerViewer(gameState).getNodeRoleById(my_id) == NodeRole.MASTER) {
            masterTimer.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!masterPlayer.play(this)) {
            stop();//changerRole.change(gamePlay, VIEWER)
        }
        getGameFieldDrawer().redrawField(this);
        Server.getNetwork().sendToListOfAddresses(
                new GamePlayersViewer(gameState).getSocketAddressOfAllPlayersWithOutMaster(),
                new StateMessageCreator(getAndIncMsgSeq(), gameState).getBytes()
        );
    }

    public void stop() {
        receiverUnicast.stop();
        Server.deleteFromCurrentGames(gameState);
        senderMulticast.stop();
        gameInfoDrawerWork = false;
        gameFieldDrawer.drawEndOfGame(gameState);
        masterTimer.stop();
        isModeButtonControllerThreadWork = false;
    }

    public GameFieldDrawer getGameFieldDrawer() {
        return gameFieldDrawer;
    }

    public GameWindowController getGameWindowController() {
        return gameWindowController;
    }

    public KeyController getKeyController() {
        return keyController;
    }

//    public ConfirmSender getConfirmSender() {
//        return confirmSender;
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

    private static final Map<NodeRole, ToViewer> stoppersMap = Map.of(
            NodeRole.MASTER, new MasterToViewer(),
            NodeRole.NORMAL, new NormalToViewer(),
            NodeRole.DEPUTY, new DeputyToViewer()
    );

    public void setMyId(int receiverId) {
        my_id = receiverId;
    }

    public void updateDeputy(Integer playerId) {
        if (new GamePlayersViewer(gameState).findDeputyPlayer() == null) {
            new GamePlayerRenovator(this).updateNodeRole(playerId, NodeRole.DEPUTY);
        }
    }

    public NodeRole getMyNodeRole() {
        return new GamePlayerViewer(gameState).getNodeRoleById(my_id);
    }

    public void setMyNodeRole(NodeRole nodeRole) {
        new GamePlayerRenovator(this).updateNodeRole(my_id, nodeRole);
    }

    public List<GameMessage> getSteerMessages() {
        return receiverUnicast.getLastSteerMsgFromStorage();
    }

    public ReceiverUnicast getReceiverUnicast() {
        return receiverUnicast;
    }

    public SocketAddress getDeputySocketAddress() {
        return new GamePlayersViewer(gameState).getDeputySocketAddress();
    }

    public Timer getMasterTimer() {
        return masterTimer;
    }
}
