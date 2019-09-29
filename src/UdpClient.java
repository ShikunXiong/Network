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
                t.sendChunk(allBuf, allBuf.length, socket, add, LIMIT, PORT,
                        this.sendbuf, this.packet);


                recBuf = new byte[1024];
                DatagramPacket recPacket = new DatagramPacket(recBuf, recBuf.length);
                socket.receive(recPacket);
                String result = new String(recBuf);
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

    public void sendChunk(byte[] allBuf, int size, DatagramSocket socket, InetAddress add){
        int start = 0;
        int end = this.LIMIT;
        while(start<size){
            this.sendbuf = new byte[this.LIMIT];
            while(end>size){
                end--;
            }
            System.arraycopy(allBuf, start, this.sendbuf, 0, end-start);
            this.packet = new DatagramPacket(this.sendbuf, this.sendbuf.length, add, this.PORT);
            try {
                socket.send(this.packet);
                System.out.println("chunk send" + this.packet.getLength());
            }catch (IOException e){
                e.printStackTrace();
            }
            start = end;
            end = start + this.LIMIT;
        }
        String stopSign = "#";
        allBuf = stopSign.getBytes();
        this.sendbuf = new byte[this.LIMIT];
        System.arraycopy(allBuf, 0, this.sendbuf, 0, 1);
        this.packet = new DatagramPacket(this.sendbuf, this.sendbuf.length, add, this.PORT);
        try {
            socket.send(this.packet);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
