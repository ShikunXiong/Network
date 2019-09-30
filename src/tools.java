import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class tools {
    private final int MAXTIME = 3;
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
            }catch (IOException e){
                e.printStackTrace();
            }
            start = end;
            end = start + limit;
        }
        String stopSign = "#";
        allBuf = stopSign.getBytes();
        sendbuf = new byte[1];
        System.arraycopy(allBuf, 0, sendbuf, 0, 1);
        packet = new DatagramPacket(sendbuf, sendbuf.length, add, port);
        try {
            socket.send(packet);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void send(byte[] allBuf, int size, DatagramSocket socket, InetAddress add,
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
            boolean flag = false;
            int times = 0;
            while(!flag && times<MAXTIME) {
                try {
                    socket.send(packet);
                    socket.setSoTimeout(3000);
                    try {
                        byte[] buf = new byte[1024];
                        DatagramPacket rectmp = new DatagramPacket(buf, buf.length);
                        socket.receive(rectmp);
                        if (!rectmp.getData().toString().equals("1")){
                            throw new IOException("Send fail");
                        }
                    } catch (Exception e){
                        System.err.println("server no response");
                    }
                } catch (IOException e) {
                    times++;
                }
                flag = true;
            }
            start = end;
            end = start + limit;
        }
        String stopSign = "#";
        allBuf = stopSign.getBytes();
        sendbuf = new byte[1];
        System.arraycopy(allBuf, 0, sendbuf, 0, 1);
        packet = new DatagramPacket(sendbuf, sendbuf.length, add, port);
            try {
                byte[] buf = new byte[1];
                DatagramPacket rectmp = new DatagramPacket(buf, buf.length);
                socket.send(packet);
                socket.receive(rectmp);
            } catch (IOException e) {
                System.err.println("Connecting fail: " + e);
                socket.close();
                System.exit(1);
            }

    }
}
