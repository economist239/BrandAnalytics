package ru.brandanalyst.assessor;


import java.io.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Senov
 * Date: 13.11.11
 * Time: 19:25
 */
@Deprecated
public class TweetsPreparer {
    private final static int DEF_NEAREST = 3;

    public void CountWordsInTweetsAsVectors(String inputTweetsFile, String inputWordsFile) {
        try {
            Map<String, Integer> tweets = getTweetsFromFile(inputTweetsFile);//
            List<String> semanticWords = getWordsFromFile(inputWordsFile);

            List<List<Integer>> resultMatrix = new ArrayList<List<Integer>>(tweets.size());
            List<Integer> resultMarks = new ArrayList<Integer>(tweets.size());
            Iterator<Map.Entry<String, Integer>> tweetsIterator = tweets.entrySet().iterator();
            int count;
            while (tweetsIterator.hasNext()) {
                Map.Entry<String, Integer> next = tweetsIterator.next();
                String tweetText = next.getKey();
                List<Integer> row = new ArrayList<Integer>(semanticWords.size());
                Iterator<String> wordsIterator = semanticWords.listIterator();
                while (wordsIterator.hasNext()) {
                    count = countsSubInString(tweetText, wordsIterator.next());
                    row.add(count);
                }
                //if(sumIntegerList(row) > 0){
                row.add(next.getValue());
                resultMatrix.add(row);

                //}
            }

            String fileName = inputTweetsFile;
            String tweetsFileName = fileName.substring(0,
                    (fileName.indexOf(".txt") > 0) ? fileName.indexOf(".txt") : fileName.length());
            fileName = inputWordsFile;
            String wordsFileName = fileName.substring(0,
                    (fileName.indexOf(".txt") > 0) ? fileName.indexOf(".txt") : fileName.length());
            String outputMatrixFile = tweetsFileName + "_" + inputWordsFile + "_matrix" + ".txt";
            String outputMarksFile = tweetsFileName + "_" + inputWordsFile + "_marks" + ".txt";
            writeMatrixToFile(resultMatrix, outputMatrixFile);
            writeTweetsMarksToFile(resultMarks, outputMarksFile);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Integer sumIntegerList(List<Integer> values) {
        int result = 0;
        for (ListIterator<Integer> it = values.listIterator(); it.hasNext(); ) {
            result += it.next();
        }
        if (result == 0) {
            result = -1;
        }
        return result;
    }

    private void writeTweetsMarksToFile(List<Integer> marks, String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        writer.println(listOfIntegersToString(marks));
        writer.close();
    }

    private void writeMatrixToFile(List<List<Integer>> matrix, String fileName) throws FileNotFoundException, IOException {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        ListIterator<List<Integer>> rowsIterator = matrix.listIterator();
        List<Integer> row;
        while (rowsIterator.hasNext()) {
            row = rowsIterator.next();
            writer.println(listOfIntegersToString(row));
        }
        writer.close();
    }

    private String listOfIntegersToString(List<Integer> list) {
        String result = "";
        ListIterator<Integer> iterator = list.listIterator();
        while (iterator.hasNext()) {
            result += " " + Integer.toString(iterator.next());
        }
        return result;
    }

    public Map<String, Integer> getTweetsFromFile(String fileName) throws FileNotFoundException, IOException {
        Map<String, Integer> result = new HashMap<String, Integer>();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        String fileLine;
        int count;
        while (null != (fileLine = reader.readLine())) {
            if (fileLine.replaceAll(" ", "") != "") {
                int index = fileLine.indexOf(";;");
                int value = 0;
                if (index > 0) {
                    try {
                        String stringValue = fileLine.substring(0, index);
                        stringValue = stringValue.replaceAll("[^0-9-+]", "");
                        value = Integer.parseInt(stringValue);
                        fileLine = fileLine.substring(index + 2, fileLine.length());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                result.put(fileLine, value);
            }
        }
        return result;
    }

    public void removeDuplicates(String inFileName, String outFileName) throws IOException {
        Set<String> result = new HashSet<String>();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(inFileName), "UTF-8"));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFileName));
        String fileLine;
        while (null != (fileLine = reader.readLine())) {
            if (fileLine.replaceAll(" ", "") != "") {
                result.add(fileLine);
            }
        }
        Iterator<String> it = result.iterator();
        while (it.hasNext()) {
            writer.write(it.next());
            writer.newLine();
        }
        writer.close();
    }


    public List<String> getWordsFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        List<String> result = new ArrayList<String>();


        StringTokenizer tokenizer;
        String fileLine;

        while (null != (fileLine = reader.readLine())) {
            tokenizer = new StringTokenizer(fileLine);
            while (tokenizer.hasMoreTokens()) {
                result.add(tokenizer.nextToken());
            }
        }
        result.remove(0);
        return result;
    }


    public int countsSubInString(String target, String sub) {
        int count = 0;
        int indexFrom = 0;
        while ((indexFrom = target.indexOf(sub, indexFrom)) > 0) {
            ++count;
            indexFrom += sub.length();
        }
        return count;
    }

    public void findNearestWordsFrequency(String tweetsFileName, String brandsFileName)
            throws IOException {
        Map<String, Integer> tweets = getTweetsFromFile(tweetsFileName);//
        List<String> brands = getWordsFromFile(brandsFileName);

        List<String> tweets_1 = getWithSpecificValue(tweets, -1);
        List<String> tweets0 = getWithSpecificValue(tweets, 0);
        List<String> tweets1 = getWithSpecificValue(tweets, 1);

        Map<String, Integer> result_1 = getNearestWordsFrequency(brands, tweets_1);
        Map<String, Integer> result0 = getNearestWordsFrequency(brands, tweets0);
        Map<String, Integer> result1 = getNearestWordsFrequency(brands, tweets1);
        printKeysToFile("_1.txt", result_1);
        printKeysToFile("0.txt", result0);
        printKeysToFile("1.txt", result1);

    }

    private List<String> getWithSpecificValue(Map<String, Integer> tweets, int value) {
        List<String> result = new ArrayList<String>();
        Iterator<Map.Entry<String, Integer>> it = tweets.entrySet().iterator();
        Map.Entry<String, Integer> entry;
        while (it.hasNext()) {
            entry = it.next();
            if (entry.getValue() == value) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public void printKeysToFile(String fileName, Map<String, Integer> map) throws IOException {
        PrintWriter writer = new PrintWriter(fileName);
        for (String s : map.keySet()) {
            writer.print(s + " ");
        }
        writer.close();
    }

    public Map<String, Integer> getNearestWordsFrequency(List<String> brands, List<String> tweets) {
        ListIterator<String> it = tweets.listIterator();
        Map<String, Integer> result = new HashMap<String, Integer>();
        while (it.hasNext()) {
            addNearestWords(result, brands, it.next());
        }
        //result = MapUtil.sortAscByValue(result);
        return result;
    }

    public Map<String, Integer> addNearestWords(Map<String, Integer> result, List<String> brands, String tweet) {
        int index;
        if ((index = whoInString(brands, tweet)) > 0) {
            String word = brands.get(index);
            List<String> nearestWords = getNearestWords(tweet, word);
            addToMap(result, nearestWords);
        }
        return result;
    }

    public Map<String, Integer> addToMap(Map<String, Integer> result, List<String> toAdd) {
        int count;
        for (String s : toAdd) {
            count = 1;
            if (result.containsKey(s)) {
                count = result.get(s) + 1;

            }
            result.put(s, count);
        }
        return result;
    }

    private List<String> getNearestWords(String s, String w) {
        List<String> result = new ArrayList<String>();
        String[] words = s.split(" ");
        int position = 0;
        while (position < words.length) {
            if (words[position].contains(w)) {
                break;
            }
            ++position;
        }
        if (words.length == position) {
            return null;
        }
        for (int i = Math.max(position - DEF_NEAREST, 0); i < Math.min(words.length, position + DEF_NEAREST); ++i) {
            if (!words[i].contains(w)) {
                result.add(words[i]);
            }
        }
        return result;
    }

    private int whoInString(List<String> words, String s) {
        int i = 0;
        for (String word : words) {
            if (s.contains(word)) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    public static void main(String[] args) {
        TweetsPreparer analyzer = new TweetsPreparer();
        //analyzer.removeDuplicates("tweets1.txt","tweets2.txt");
        //analyzer.findNearestWordsFrequency("corpus_processed.txt","brands.txt");
        analyzer.CountWordsInTweetsAsVectors("corpus_processed.txt", "allvars.txt");
    }
}
