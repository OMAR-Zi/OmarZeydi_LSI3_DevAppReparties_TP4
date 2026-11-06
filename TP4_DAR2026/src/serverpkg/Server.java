package serverpkg;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Server {
    static byte buffer[] = new byte[1024];

    public static void main(String argv[]) throws Exception {
        InetSocketAddress addr = new InetSocketAddress(1234);
        DatagramSocket socket=new DatagramSocket(null);
        socket.bind(addr);
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            System.out.println("The server is waiting for packets..");
            socket.receive(packet);
            int taille = packet.getLength();
            String donnees = new String(packet.getData(), 0, taille);
            System.out.println("De"+packet.getSocketAddress()+":"+donnees);

        }
    }
}
