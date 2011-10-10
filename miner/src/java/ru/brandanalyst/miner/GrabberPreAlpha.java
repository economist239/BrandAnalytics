/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.brandanalyst.miner;
//
import java.io.*;
import java.lang.*;
import static java.lang.System.*;

/**
 *
 * @author Александр
 */

public class GrabberPreAlpha {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        System.out.println("Miner started...");
        // TODO code application logic here
    //   try{
    //      FileOutputStream fos = new FileOutputStream("C:\\markov_new.txt");
    //      FileInputStream fis = new FileInputStream("C:\\markov.txt");
    //      byte[] b = new byte[fis.available()];
    //      fis.read(b);
    //      fos.write(b);
        //markov?=)
            GrabberYandex grabberYandex = new GrabberYandex(args[0]);
            System.out.println(grabberYandex.grab()[0]);
    //   }
    //   catch(IOException ioe){
    //       System.out.println("file not found");
    //   }
        System.out.println("Miner finished.");
    }
}
