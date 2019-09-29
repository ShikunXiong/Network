import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class CountLines {


    public static void main(String args[]){
        List<String> files = new ArrayList<>();
        files.add("files/c1.txt");
        files.add("files/c2.txt");
        countLines(files);
    }

    public static void  countLines(List<String> list){
        String result = "";
        for (String s:list){
            int count = 0;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader
                        (new FileInputStream(new File(s)),"UTF-8"));
                String line = "";
                while((line = reader.readLine())!=null){
                    count+=1;
                }


            }catch (Exception e){
                System.err.println("Error Read" + e);
            }

            result += s.substring(6) + " " + count + "\n";

        }
        System.out.println(result);
    }
}
