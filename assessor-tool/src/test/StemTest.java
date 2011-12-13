import org.junit.Ignore;
import org.junit.Test;

import org.tartarus.snowball.ext.RussianStemmer;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Senov
 * Date: 12/13/11
 * Time: 11:24 PM
 */
@Deprecated
@Ignore
public class StemTest {
    BufferedReader input;
    FileWriter output;
    StringTokenizer reader;

    public void setInput(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        this.input = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        reader = new StringTokenizer("");
    }

    public void setOutput(String fileName) throws IOException {
        this.output = new FileWriter(fileName);
    }

    public String nextWord() throws IOException {
        if (null != input) {

            if (reader.hasMoreTokens()) {
                return reader.nextToken();
            } else if (input.ready()) {
                reader = new StringTokenizer(input.readLine());
                return reader.nextToken();
            }
            return null;
        } else {
            throw new IOException("Файл забыл инициализировать, дуреха");
        }
    }

    public void stemFile(String fileName) throws IOException {
        setInput(fileName);
        setOutput(fileName.substring(0,
                (fileName.indexOf(".txt") > 0) ? fileName.indexOf(".txt") : fileName.length()) + "_stemmed" + ".txt");
        RussianStemmer stemmer = new RussianStemmer();
        String nextToStem;
        while ((nextToStem = this.nextWord()) != null) {
            stemmer.setCurrent(nextToStem);
            boolean stemmerResult = stemmer.stem();
            if (false == stemmerResult) {
                System.out.println("Что-то не удалось: " + nextToStem + " - " + stemmer.getCurrent());
            }
            output.write(stemmer.getCurrent() + " ");
        }
        stemmer.stem();
        output.close();
    }

    @Test
    public static void test() throws IOException {
        StemTest test = new StemTest();
        System.out.println("Введите имя фаила с словами для стемминга");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String temp = reader.readLine();
        test.stemFile(temp);
    }


}
