package game.multi;

import com.google.protobuf.InvalidProtocolBufferException;
import dto.GameMessage;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Network {
    private static final String MULTICAST_IP = "239.192.0.4";
    private static final int MULTICAST_PORT = 9192;
    private static final int BUFF_SIZE = 1024;

    private static InetAddress group;
    private static DatagramSocket unicastSocket;
    private static MulticastSocket multicastSocket;

    public Network() {
        try {
            group = InetAddress.getByName(MULTICAST_IP);

            multicastSocket = new MulticastSocket(MULTICAST_PORT);
            multicastSocket.joinGroup(group);

            unicastSocket = new DatagramSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToGroup(byte[] bytes) {
        DatagramPacket packetToGroup = new DatagramPacket(bytes, bytes.length, group, MULTICAST_PORT);
        try {
            unicastSocket.send(packetToGroup);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<SocketAddress, byte[]> receiveFromMulticast() {
        byte[] buffer = new byte[BUFF_SIZE];
        DatagramPacket packetFromGroup = new DatagramPacket(buffer, buffer.length);
        try {
            multicastSocket.receive(packetFromGroup);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] answer = new byte[packetFromGroup.getLength()];
        if (packetFromGroup.getLength() >= 0)
            System.arraycopy(buffer, 0, answer, 0, packetFromGroup.getLength());

        Map<SocketAddress, byte[]> receivedMap = new ConcurrentHashMap<>();
        receivedMap.put(packetFromGroup.getSocketAddress(), answer);

        return receivedMap;
    }

    public void sendToSocket(byte[] bytes, SocketAddress socketAddress) {
        try {
            DatagramPacket sendingPacket = new DatagramPacket(bytes, bytes.length,
                    socketAddress);
            unicastSocket.send(sendingPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] receiveFromSocket() throws IOException {
        byte[] buffer = new byte[BUFF_SIZE];
        DatagramPacket packetFromGroup = new DatagramPacket(buffer, buffer.length);
        unicastSocket.receive(packetFromGroup);
        return buffer;
    }

    public void stop() {
        unicastSocket.close();
        multicastSocket.close();
    }

    public int getUnicastSocketPort() {
        return unicastSocket.getLocalPort();
    }
}
