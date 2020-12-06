package game.multi.receive.handlers;

import dto.GameMessage;
import dto.NodeRole;
import game.multi.Server;
import game.multi.proto.creators.AckMessageCreator;
import game.multi.proto.creators.ErrorMessageCreator;
import game.multi.proto.renovators.GameStateRenovator;
import game.multi.receive.ReceiverFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class JoinMessageHandler implements MessageHandler {
    private final String ERROR_MESSAGE = "Can't join to game";
    @Override
    public void handle(InetSocketAddress socketAddress, ReceiverFactory receiverFactory, GameMessage currentMessage) {
        System.out.println("I got join from " + socketAddress);
        String name = currentMessage.getJoin().getName();
        NodeRole nodeRole;
        if (currentMessage.getJoin().getOnlyView()) {
            nodeRole = NodeRole.VIEWER;
        } else {
            nodeRole = NodeRole.NORMAL;
        }
        GameStateRenovator gameStateRenovator = new GameStateRenovator(receiverFactory.getGame());
        Integer playerId = gameStateRenovator.tryAddNewPlayer(name, socketAddress, nodeRole);
        if (playerId == null) {
            Server.getNetwork().sendToSocket(
                    new ErrorMessageCreator(
                            receiverFactory.getGame().getAndIncMsgSeq(),
                            ERROR_MESSAGE).getBytes(),
                    socketAddress
            );
        } else {
            Server.getNetwork().sendToSocket(
                    new AckMessageCreator(receiverFactory.getGame().getAndIncMsgSeq(), playerId).getBytes(),
                    socketAddress
            );
        }
    }
}
