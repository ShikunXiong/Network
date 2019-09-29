import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class UdpServer {
    private final int MAX_LENGTH = 1024;
    private final int LIMIT = 3;
    private final int PORT = 5555;
    private String backMess = "";
    private DatagramPacket recPacket;
    private String current = "";
    private String rec = "";

    private byte[] income;

    public static void main(String[] args){
        UdpServer server = new UdpServer();
        server.startServer();
    }

    public void startServer(){
        try{
            DatagramSocket socket = new DatagramSocket(PORT);
            // Receive
            while (true) {
                rec = "";
                System.out.println("UDPServer starts\r\n");
                while (true) {
                    income = new byte[LIMIT];
                    recPacket = new DatagramPacket(income, income.length);
                    socket.receive(recPacket);
                    System.out.println("chunk receive");
                    current = new String(recPacket.getData()).trim();
                    System.out.println(""+current);
                    if(current.equals("#")){
                        break;
                    }
                    else{
                        rec+=current;
                        System.out.println(rec);
                    }

                }
                //Process Rec
                if (rec.equals("index")){
                    File file = new File("files/");
                    List<File> l = new ArrayList<>();
                    l = listFiles(file);
                    for(File f:l){
                        System.out.println(f.getName());
                        backMess += f.getName() + "\r\n";
                    }
                }else{
                    int end = rec.length();
                    if(end>4){
                        rec = rec.substring(4, end - 1);
                        String path = "files/" + rec;
                        if (new File(path).exists()){
                            backMess = "ok\r\n";
                        }else{
                            backMess = "no found\r\n";
                        }
                    }else{
                        backMess = "no found\r\n";
                    }
                }
                // Send back
                byte[] sendBack = backMess.getBytes();
                DatagramPacket backPacket = new DatagramPacket(sendBack, sendBack.length,
                        recPacket.getAddress(), recPacket.getPort());
                socket.send(backPacket);
            }

        }catch (SocketException e){
            e.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static List listFiles(File file) {
        List<File> fileList = new ArrayList<>();
        if (file.isDirectory()) {
            for (File listFile : file.listFiles()) {
                fileList.addAll(listFiles(listFile));
            }
        } else {
            fileList.add(file);
        }
        return fileList;
    }


}
