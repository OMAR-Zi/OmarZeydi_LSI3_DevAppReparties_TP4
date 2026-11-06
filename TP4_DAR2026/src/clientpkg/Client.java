package clientpkg;


import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;



public class Client{
    public static void main(String argv[]) {
        try(DatagramSocket socket = new DatagramSocket();Scanner scanner=new Scanner(System.in)){
            System.out.println("Enter your fullName: ");
            String name=scanner.nextLine();
            InetAddress serveraddr =InetAddress.getByName("localhost");


            System.out.println("Write your messages(E to exit)");
            while(true) {
                String text=scanner.nextLine();
                if (text.equals("E")) break;
                byte[] data=("["+name+"] : "+text).getBytes(StandardCharsets.UTF_8);
                socket.send(new DatagramPacket(data, data.length,serveraddr,1234));

            }

        }catch (Exception e) {
            System.out.println("Client error: "+e.getMessage());
        }




    }
}