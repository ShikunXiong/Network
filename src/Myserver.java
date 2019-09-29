import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Myserver {

    public static void main(String args[]) {
        StartServer();
    }

    public static void StartServer() {
        try {
            ServerSocket server = new ServerSocket(13);
            while (true) {
                try {
                    Socket connection = server.accept();
                    Thread task = new myTask(connection);
                    while(true) {
                        task.run();
                    }
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    private static class myTask extends Thread{
        private Socket connection;

        myTask(Socket connection){
            this.connection = connection;
        }

        @Override
        public void run(){
            try{
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                bw.write("Input(index or get<filename>):\r\n");
                bw.flush();

                String mess = br.readLine();
                if (mess.equals("index")) {
                    File f = new File("files/");
                    List<File> l = new ArrayList<>();
                    l = listFiles(f);
                    bw.write("Files included are:\r\n");
                    for (File o : l) {
                        bw.write(o.getName() + "\r\n");
                    }
                    bw.write("\r\n");
                    bw.flush();
                    //connection.close();
                } else {
                    int end = mess.length();
                    if (end>4) {
                        mess = mess.substring(4, end - 1);
                        String path = "files/" + mess;
                        String result;
                        if (new File(path).exists()) {
                            result = "ok";
                        } else {
                            result = "error";
                        }
                        bw.write("The request result: " + result + "\r\n");
                        bw.write("\r\n");
                        bw.flush();
                    }
                    else{
                        bw.write("Wrong input\r\n");
                        bw.write("\r\n");
                        bw.flush();
                    }

                }
            }catch (IOException e){
                System.err.println(e);
            }
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