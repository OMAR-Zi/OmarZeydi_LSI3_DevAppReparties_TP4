package clientpkg;


import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;



public class Client extends Thread {

    private final DatagramSocket socket;
    public Client(DatagramSocket socket){
        this.socket=socket;
        setDaemon(true); //the thread stops when main finishes
    }
    // run for receiving
    @Override
    public void run(){
        byte[] buf=new byte[1024];
        try {
            while (!socket.isClosed()){
                DatagramPacket p = new DatagramPacket(buf, buf.length);
                socket.receive(p);
                System.out.println(new String(p.getData(), 0, p.getLength()));
            }
        }catch (Exception e){
            if (!socket.isClosed()) System.out.println("Receiver error: "+e.getMessage());
        }
    }
    //main for sending
    public static void main(String argv[]) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter your fullName: ");
        String name=scanner.nextLine();
        InetAddress serveraddr =InetAddress.getByName("localhost");
        new Client(socket).start();
        send(socket,serveraddr,"["+name+"] joined Chat");
        System.out.println("Write your messages(E to exit)");
        while(true) {
            String text=scanner.nextLine();
            if (text.equals("E")) break;
            send(socket,serveraddr,"["+name+"] : "+text);
        }
        socket.close();
        scanner.close();
    }

    private static void send(DatagramSocket s,InetAddress addr,String msg) throws Exception{
        byte[] data=msg.getBytes(StandardCharsets.UTF_8);
        s.send(new DatagramPacket(data, data.length,addr,1234));
    }
}