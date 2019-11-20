import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Myserver {

    public static void main(String args[]) {
        StartServer();
        // telnet localhost 13
    }

    public static void StartServer() {
        try {
            ServerSocket server = new ServerSocket(13);
            Socket connection = server.accept();
            System.out.println("Server connected");
            PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while (true) {
                String mess = br.readLine();
                String result = "";
                boolean flag = true;
                System.out.println("accept " + mess);
                if (mess.equals("index")) {
                    File f = new File("files/");
                    List<File> l = new ArrayList<>();
                    l = listFiles(f);
                    result += "Files included are:\n";
                    for (File o : l) {
                        result += o.getName() + "\n";
                    }
                } else {
                    int end = mess.length();
                    if (end>4) {
                        mess = mess.substring(4, end - 1);
                        String path = "files/" + mess;
                        if (new File(path).exists()) {
                            result = "ok\n";
                            FileInputStream inputStream = new FileInputStream(path);
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                            String str = null;
                            while((str = bufferedReader.readLine()) != null)
                            {
                                result += str + "\n";
                            }
                            inputStream.close();
                            bufferedReader.close();
                        } else {
                            result = "error";
                            flag = false;
                        }
                    }
                    else{
                        result = "error";
                        flag = false;
                    }
                    }
                    out.println(result);
                    out.flush();
                    if(flag == false){
                        System.out.println("close");
                        connection.close();
                        break;
                    }
            }
            System.exit(0);
        } catch (IOException ex) {
            System.out.println("no files");
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