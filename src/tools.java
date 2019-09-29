import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class tools {
    public void sendChunk(byte[] allBuf, int size, DatagramSocket socket, InetAddress add,
                          int limit, int port,byte[] sendbuf, DatagramPacket packet){
        int start = 0;
        int end = limit;
        while(start<size){
            sendbuf = new byte[limit];
            while(end>size){
                end--;
            }
            System.arraycopy(allBuf, start, sendbuf, 0, end-start);
            packet = new DatagramPacket(sendbuf, sendbuf.length, add, port);
            try {
                socket.send(packet);
                System.out.println("chunk send" + packet.getLength());
            }catch (IOException e){
                e.printStackTrace();
            }
            start = end;
            end = start + limit;
        }
        String stopSign = "#";
        allBuf = stopSign.getBytes();
        sendbuf = new byte[limit];
        System.arraycopy(allBuf, 0, sendbuf, 0, 1);
        packet = new DatagramPacket(sendbuf, sendbuf.length, add, port);
        try {
            socket.send(packet);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
