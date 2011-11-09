package ru.brandanalyst.assessor;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/9/11
 * Time: 12:26 PM
 */

import java.io.*;

public final class TwitterTool {
    public static void main(String[] args) {
        PrintWriter pw;
        BufferedReader br;
        try {
            pw = new PrintWriter(args[0].substring(0, args[0].indexOf(".")) + "_processed.txt");
            br = new BufferedReader(new FileReader(args[0]));
            while (br.ready()) {
                String tweet = br.readLine();
                System.out.println(tweet);
                byte[] read = new byte[1];
                System.in.read(read);
                switch ((char)read[0]) {
                    case '1':
                        pw.write("1;;" + tweet + "\n");
                        break;
                    case '3':
                        pw.write("-1;;" + tweet + "\n");
                        break;
                    case '2':
                        pw.write("0;;" + tweet + "\n");
                        break;
                    case '+':
                        pw.close();
                        br.close();
                        System.exit(0);
                }
            }
            pw.close();
            br.close();
            System.out.println("assessor tool started");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("io exception");
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("ты тупой?");
        }
    }


}
