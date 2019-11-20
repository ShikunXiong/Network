import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) {
        try {
            Socket st = new Socket("localhost", 8000);
            PrintWriter out = new PrintWriter(st.getOutputStream(), true);// to server
            BufferedReader in = new BufferedReader(new InputStreamReader(st.getInputStream()));// from server
            BufferedReader keyboardIn = new BufferedReader(new InputStreamReader(System.in));// from keyboard
            while (true) {
                System.out.print("client> ");
                String command = keyboardIn.readLine();
                out.println(command);
                out.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
