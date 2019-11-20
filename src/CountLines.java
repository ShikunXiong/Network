import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CountLines {


    public static void main(String args[]){
        System.out.println("please input file names:");
        Scanner scanner = new Scanner(System.in);
        List<String> files = new ArrayList<>();
        File dir = new File("files/");
        String[] f = dir.list();
        String str1 = "";
        if (scanner.hasNextLine()) {
            str1 = scanner.nextLine();
        }

        String [] arr = str1.split("\\s+");
        for (String x: arr){
            files.add(x);
        }
        countLines(files, arr);
    }

    public static void  countLines(List<String> list,  String [] arr){
        String base_path = "files/";
        String result = "";
        String no_exist = "";
        for (String s:list) {
            boolean flag = false;
            for (String f : arr) {
                if (f.equals(s)){
                    flag = true;
                    int count = 0;
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader
                                (new FileInputStream(new File(base_path+s)), "UTF-8"));
                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            count += 1;
                        }
                        result += s + " " + count + "\n";
                    } catch (Exception e) {
                    }
                break;
                }
            }
            if(!flag) {
                no_exist += s + " doesn't exist";
            }
        }
        System.out.println(result);
        System.out.println(no_exist);
    }
}
