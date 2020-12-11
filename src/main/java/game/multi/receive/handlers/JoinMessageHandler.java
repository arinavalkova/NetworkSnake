package game.multi.receive.handlers;

import dto.GameMessage;
import dto.NodeRole;
import game.multi.GamePlay;
import game.multi.Server;
import game.multi.proto.creators.AckMessageCreator;
import game.multi.proto.creators.ErrorMessageCreator;
import game.multi.proto.renovators.GameStateRenovator;
import game.multi.receive.ReceiverMulticast;

import java.net.InetSocketAddress;

public class JoinMessageHandler implements MessageHandler {
    private final String ERROR_MESSAGE = "Can't join to game";

    @Override
    public void handle(InetSocketAddress socketAddress, GamePlay gamePlay, GameMessage currentMessage) {
        String name = currentMessage.getJoin().getName();
        NodeRole nodeRole;
        if (currentMessage.getJoin().getOnlyView()) {
            nodeRole = NodeRole.VIEWER;
            int player_id = new GameStateRenovator(gamePlay).addViewerPlayer(socketAddress, name);
            Server.getNetwork().sendToSocket(
                    new AckMessageCreator(
                            (int) currentMessage.getMsgSeq(),
                            gamePlay.getMy_id(),
                            player_id).getBytes(),
                    socketAddress
            );
            return;
        } else {
            nodeRole = NodeRole.NORMAL;
        }
        GameStateRenovator gameStateRenovator = new GameStateRenovator(gamePlay);
        Integer playerId = gameStateRenovator.tryAddNewPlayer(
                name,
                socketAddress,
                nodeRole,
                currentMessage.getSenderId()
        );
        if (playerId == null) {
            Server.getNetwork().sendToSocket(
                    new ErrorMessageCreator(
                            (int) currentMessage.getMsgSeq(),
                            ERROR_MESSAGE).getBytes(),
                    socketAddress
            );
        } else {
            Server.getNetwork().sendToSocket(
                    new AckMessageCreator(
                            (int) currentMessage.getMsgSeq(),
                            gamePlay.getMy_id(),
                            playerId).getBytes(),
                    socketAddress
            );
        }
        gamePlay.updateDeputy();
    }
}
