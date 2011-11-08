import twitter4j.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/4/11
 * Time: 11:52 AM
 */
public class TwitterWrapperToFile {
    private static final int ISSUANCE_SIZE = 1500;
    private static final int PAGE_SIZE = 100;

    public static void main(String[] args) {

        Twitter twitter = new TwitterFactory().getInstance();

        List<String> brandList = new ArrayList<String>();
        brandList.add("Apple");

        brandList.add("Apple");
        for (String b : brandList) {
            try {
                Query query = new Query(b);
                query.setRpp(PAGE_SIZE);
                query.setSince("2009" + "-" + "01" + "-" + "01");
                query.setLang("ru");
                query.setResultType(Query.MIXED);
                List<Tweet> resultTweets = new ArrayList<Tweet>();
                QueryResult queryResult;

                PrintWriter pw = new PrintWriter("tweets.txt");
                int pageNumber = 1;
                do {
                    query.setPage(pageNumber);
                    queryResult = twitter.search(query);
                    resultTweets.addAll(queryResult.getTweets());
                    pageNumber++;
                } while (ISSUANCE_SIZE > resultTweets.size());
                for (int i = 0; i < ISSUANCE_SIZE; i++) {
                    String str = resultTweets.get(i).getText();
                    int index = resultTweets.get(i).getText().indexOf("http");
                    if (index > 0) {
                        str = str.substring(0, index);
                    }
                    pw.println(str);
                }
                pw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
