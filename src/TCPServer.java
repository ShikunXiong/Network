import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8000);
            Socket st = ss.accept();
            System.out.println("Server connected");
            BufferedReader in = new BufferedReader(new InputStreamReader(st.getInputStream()));// from client
            PrintWriter out = new PrintWriter(st.getOutputStream(), true);// to client
            while (true) {
                String command = in.readLine();
                System.out.println(command);
                out.flush();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
