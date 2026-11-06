package serverpkg;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Set;

public class Server {

    public static void main(String argv[]) throws Exception {

        InetSocketAddress addr = new InetSocketAddress(1234);
        DatagramSocket socket=new DatagramSocket(null);
        socket.bind(addr);
        System.out.println("The server is waiting for packets..");
        Set<SocketAddress> clients=new LinkedHashSet<>();
        byte buffer[] = new byte[1024];

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            SocketAddress sender= packet.getSocketAddress();
            if (clients.add(sender)) {
                System.out.println("New client: "+sender);
            }

            int taille = packet.getLength();
            String donnees = new String(packet.getData(), 0,taille);
            System.out.println("De"+sender+":"+donnees);

            byte[] data =donnees.getBytes(StandardCharsets.UTF_8);
            for (SocketAddress c : clients){
                if (!c.equals(sender)){
                    socket.send(new DatagramPacket(data, data.length,c));
                }
            }

        }
    }
}
