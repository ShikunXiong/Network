import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class UdpServer {
    private final int MAX_LENGTH = 3;
    private final int LIMIT = 3;
    private byte[] sendbuf = new byte[LIMIT];
    private final int PORT = 1111;
    private String backMess = "";
    private DatagramPacket recPacket;
    private DatagramPacket sendPacket;
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
            tools t = new tools();
            // Receive
            while (true) {
                rec = "";
                while (true) {
                    income = new byte[LIMIT];
                    recPacket = new DatagramPacket(income, income.length);
                    socket.receive(recPacket);
                    String verify = "1";
                    sendPacket = new DatagramPacket(verify.getBytes(),
                            verify.getBytes().length,
                            recPacket.getAddress(), recPacket.getPort());
                    socket.send(sendPacket);
                    //System.out.println("chunk receive");
                    current = new String(recPacket.getData()).trim();
                    if(current.equals("#")){
                        break;
                    }
                    else{
                        rec+=current;
                    }
                }
                //Process Rec
                //System.out.println(rec);
                backMess = "";
                if (rec.equals("index")){
                    File file = new File("files/");
                    List<File> l = new ArrayList<>();
                    l = listFiles(file);
                    for(File f:l){
                        backMess += f.getName() + "\n";
                    }
                }else{
                    int end = rec.length();
                    if(end>4){
                        rec = rec.substring(4, end - 1);
                        if (rec.length()==0){
                            backMess = "no found\r\n";
                        }else {
                            String path = "files/" + rec;
                            System.out.println(path);
                            if (new File(path).exists()) {
                                backMess = "ok\r\n";
                                FileInputStream inputStream = new FileInputStream(path);
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                                String str = null;
                                while((str = bufferedReader.readLine()) != null)
                                {
                                    backMess += str + "\n";
                                }
                            } else {
                                backMess = "no found\r\n";
                            }
                        }
                    }else{
                        backMess = "no found\r\n";
                    }
                }
                System.out.println("backMess:"+" "+backMess);
                // Send back
                byte[] sendBack = backMess.getBytes();
                t.sendChunk(sendBack, sendBack.length, socket,
                        recPacket.getAddress(), LIMIT, recPacket.getPort(), sendbuf, sendPacket);
            }

        }catch (SocketException e){

        }catch (IOException ex){

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
