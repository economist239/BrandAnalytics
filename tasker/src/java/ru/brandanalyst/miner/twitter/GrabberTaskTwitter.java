package ru.brandanalyst.miner.twitter;

import org.apache.log4j.Logger;
import ru.brandanalyst.core.db.provider.interfaces.ArticleProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.miner.AbstractGrabberTask;
import twitter4j.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Александр Сенов
 * Date: 10.10.11
 * Time: 14:14
 */
public class GrabberTaskTwitter extends AbstractGrabberTask {
    private static final Logger log = Logger.getLogger(GrabberTaskTwitter.class);

    private static final int ISSUANCE_SIZE = 15000;
    private static final int PAGE_SIZE = 1000;

    public void grab() {
        grab(new Date());
    }

    public void grab(Date timeLimit) {
        log.info("Twitter grabber started...");
        Twitter twitter = new TwitterFactory().getInstance();

        List<Brand> brandList = handler.getBrandProvider().getAllBrands();
        ArticleProvider articleProvider = handler.getArticleProvider();

        for (Brand b : brandList) {
            Query query = new Query(b.getName());
            query.setRpp(PAGE_SIZE);
            query.setLang("ru");
            query.setResultType(Query.MIXED);

            List<Tweet> resultTweets = new LinkedList<Tweet>();
            QueryResult queryResult;
            int pageNumber = 1;

            try {
                do {
                    query.setPage(pageNumber);
                    queryResult = twitter.search(query);
                    resultTweets.addAll(queryResult.getTweets());
                    pageNumber++;
                    log.info(pageNumber);
                } while (ISSUANCE_SIZE > resultTweets.size());
            } catch (TwitterException e) {
                throw new RuntimeException(e);
            }
            log.info("tweets in da111y: " + resultTweets.size());
            twitter = null;
            twitter = new TwitterFactory().getInstance();

            for (Tweet t : resultTweets) {
                articleProvider.writeArticleToDataStore(new Article(-1, b.getId(), 2,
                        "", t.getText(), "", getSimpleTime(t.getCreatedAt()), 1));
            }
        }
        log.info("twitter grabber finished succesful.");
    }

    private static Timestamp getSimpleTime(Date date) {
        return new Timestamp((new Date(date.getYear(), date.getMonth(), date.getDay())).getTime());
    }
}
