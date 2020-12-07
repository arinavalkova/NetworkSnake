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
        System.out.println("I got join from " + socketAddress);
        String name = currentMessage.getJoin().getName();
        NodeRole nodeRole;
        if (currentMessage.getJoin().getOnlyView()) {
            nodeRole = NodeRole.VIEWER;
        } else {
            nodeRole = NodeRole.NORMAL;
        }
        GameStateRenovator gameStateRenovator = new GameStateRenovator(gamePlay);
        Integer playerId = gameStateRenovator.tryAddNewPlayer(name, socketAddress, nodeRole);
        if (playerId == null) {
            Server.getNetwork().sendToSocket(
                    new ErrorMessageCreator(
                            gamePlay.getAndIncMsgSeq(),
                            ERROR_MESSAGE).getBytes(),
                    socketAddress
            );
        } else {
            Server.getNetwork().sendToSocket(
                    new AckMessageCreator(gamePlay.getAndIncMsgSeq(), playerId).getBytes(),
                    socketAddress
            );
        }
    }
}
