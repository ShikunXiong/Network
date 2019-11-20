import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 13;


    public static void main(String args[]) {
        try {
            Socket s = new Socket(HOSTNAME, PORT);
            s.setSoTimeout(2000);
            InputStream isr = s.getInputStream();
            BufferedReader readin = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            while(true) {
                System.out.println("input'index' or 'get<file>':");
                String in = userIn.readLine();
                out.println(in);
                out.flush();
                try {
                    String l;
                    while ((l=readin.readLine())!=null) {
                        if(l.equals("error")){
                            System.exit(0);
                        }
                        System.out.println(l);
                    }
                } catch(IOException e){
            }
            }
        }catch (IOException e){
        }

    }
}
