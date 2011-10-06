/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package grabber;
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
        // TODO code application logic here
        try{
            FileOutputStream fos = new FileOutputStream("F:\\markov_new.txt");
            FileInputStream fis = new FileInputStream("F:\\markov.txt");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fos.write(b);
            GrabberYandex grabberYandex = new GrabberYandex();
            out.println(grabberYandex.grab()[0]);
                       
        }
        catch(IOException ioe){
        }
        
        
    }
}
