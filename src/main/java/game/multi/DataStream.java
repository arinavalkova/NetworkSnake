package game.multi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class DataStream {
    private static final String MULTICAST_IP = "239.192.0.4";
    private static final int MULTICAST_PORT = 9192;
    private static final int BUFF_SIZE = 1024;

    private static InetAddress group;
    private static MulticastSocket multicastSocket;
    private static MulticastSocket receiveSocket;

    public DataStream() {
        try {
            group = InetAddress.getByName(MULTICAST_IP);

            multicastSocket = new MulticastSocket();
            multicastSocket.joinGroup(group);

            receiveSocket = new MulticastSocket(MULTICAST_PORT);
            receiveSocket.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToGroup(byte[] bytes) {
        DatagramPacket packetToGroup = new DatagramPacket(bytes, bytes.length, group, MULTICAST_PORT);
        try {
            multicastSocket.send(packetToGroup);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] receiveFromMulticast() {
        byte[] buffer = new byte[BUFF_SIZE];
        DatagramPacket packetFromGroup = new DatagramPacket(buffer, buffer.length);
        try {
            receiveSocket.receive(packetFromGroup);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
