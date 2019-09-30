import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UdpClient {
    private final int LIMIT = 3;
    private String address = "127.0.0.1";
    private String send;
    private final int PORT = 5555;
    private Scanner scanner;
    private byte[] allBuf = new byte[8192];
    private byte[] sendbuf = new byte[LIMIT];
    private byte[] recBuf;
    private DatagramSocket socket;
    private DatagramPacket packet;

    public static void main(String[] args){
        UdpClient client = new UdpClient();
        client.startClient();
    }

    public void startClient(){
        tools t = new tools();
        try {
            socket = new DatagramSocket();
            InetAddress add = InetAddress.getByName(address);
            // Prepare packet
            while(true) {
                send = "";
                scanner = new Scanner(System.in);
                System.out.println("Your input:");
                if (scanner.hasNext()) {
                    send = scanner.nextLine();
                }
                // send Packet
                allBuf = send.getBytes();
                t.send(allBuf, allBuf.length, socket, add, LIMIT, PORT,
                        this.sendbuf, this.packet);

                //receive Packet
                String result = "";
                while(true) {
                    recBuf = new byte[LIMIT];
                    DatagramPacket recPacket = new DatagramPacket(recBuf, recBuf.length);
                    socket.receive(recPacket);
                    String current = new String(recPacket.getData()).trim();
                    if(current.equals("#")){
                        break;
                    }
                    else{
                        result += current;
                    }
                }
                System.out.println(result);
            }

        }catch (SocketException e){
            e.printStackTrace();
        }catch (UnknownHostException unknown){
            unknown.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }

    }


}
