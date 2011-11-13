package ru.brandanalyst.assessor;




import java.io.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Senov
 * Date: 13.11.11
 * Time: 19:25

 */
public class TweetsPreparer {
   public void CountWordsInTweetsAsVectors(){
        try{
            Map<String,Integer> tweets = getTweetsFromFile("microsoft.txt");//
            List<String> semanticWords = getWordsFromFile("positive.txt");

            List<List<Integer>> resultMatrix = new ArrayList<List<Integer>>(tweets.size());
            Iterator<Map.Entry<String,Integer>> tweetsIterator = tweets.entrySet().iterator();
            int count;
            while(tweetsIterator.hasNext()){
                String tweetText = tweetsIterator.next().getKey();
                List<Integer> row = new ArrayList<Integer>(semanticWords.size());
                Iterator<String> wordsIterator = semanticWords.listIterator();
                while(wordsIterator.hasNext()){
                    count = countsSubInString(tweetText, wordsIterator.next());
                    row.add(count);
                }
                resultMatrix.add(row);
            }
            writeMatrixToFile(resultMatrix,"test.txt");



        }
        catch(Exception e){

        }
    }
    private void writeMatrixToFile(List<List<Integer>> matrix, String fileName)throws FileNotFoundException, IOException{
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        ListIterator<List<Integer>> rowsIterator = matrix.listIterator();
        List<Integer> row;
        while(rowsIterator.hasNext()){
            row = rowsIterator.next();
            writer.println(listOfIntegersToString(row));
        }
        writer.close();
    }
    private String listOfIntegersToString(List<Integer> list){
        String result = "";
        ListIterator<Integer> iterator = list.listIterator();
        while(iterator.hasNext()){
            result+=" " + Integer.toString(iterator.next());
        }
        return result;
    }
    public Map<String,Integer> getTweetsFromFile(String fileName)throws FileNotFoundException, IOException{
        Map<String,Integer> result = new HashMap<String,Integer>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String fileLine;
        int count;
        while(null != (fileLine = reader.readLine())){
            if(fileLine.replaceAll(" ","")!= ""){
                int index = fileLine.indexOf(";;");
                int value = 0;
                if(index>0){
                    value = Integer.parseInt(fileLine.substring(0,index));
                    fileLine = fileLine.substring(index+2,fileLine.length()) ;
                }
                result.put(fileLine,value);
            }
        }
        return result;
    }


    public List<String> getWordsFromFile(String fileName) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        List<String> result = new ArrayList<String>();

        StringTokenizer tokenizer            ;
        String fileLine;

        while(null != (fileLine = reader.readLine())){
            tokenizer = new StringTokenizer(fileLine);
            while(tokenizer.hasMoreTokens()){
                result.add(tokenizer.nextToken());
            }
        }
        result.remove(0);
        return result;
    }


    public int countsSubInString(String target,String sub){
           int count = 0;
           int indexFrom = 0;
           while( (indexFrom = target.indexOf(sub,indexFrom)) > 0){
               ++count;
               indexFrom += sub.length();
           }
           return count;
       }


    public static void main(String[] args){
        TweetsPreparer analyzer = new TweetsPreparer();
        analyzer.CountWordsInTweetsAsVectors();
    }
}
