package ru.brandanalyst.miner;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.ArticleProvider;
import ru.brandanalyst.core.db.provider.BrandDictionaryProvider;
import ru.brandanalyst.core.db.provider.BrandProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.BrandDictionaryItem;
import ru.brandanalyst.miner.util.StringChecker;
import twitter4j.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Александр Сенов
 * Date: 10.10.11
 * Time: 14:14
 */
public class GrabberTwitter extends Grabber {
    private static final Logger log = Logger.getLogger(GrabberTwitter.class);

    private static final int ISSUANCE_SIZE = 1500;
    private static final int PAGE_SIZE = 100;

    @Deprecated
    public void setConfig(String config) {
        this.config = config;  //not using
    }

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void grab(Date timeLimit) {
        log.info("Twitter grabber started...");
        Twitter twitter = new TwitterFactory().getInstance();

        List<Brand> brandList = new BrandProvider(jdbcTemplate).getAllBrands();
        ArticleProvider articleProvider = new ArticleProvider(jdbcTemplate);
        BrandDictionaryProvider dictionaryProvider = new BrandDictionaryProvider(jdbcTemplate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String stringTimeLimit = dateFormat.format(timeLimit);

        for (Brand b : brandList) {
            BrandDictionaryItem dictionary = dictionaryProvider.getDictionaryItem(b.getId());
            Query query = new Query(b.getName());
            query.setRpp(PAGE_SIZE);
            query.setSince(stringTimeLimit);
            query.setLang("ru");
            query.setResultType(Query.MIXED);

            List<Tweet> resultTweets = new LinkedList<Tweet>();

            QueryResult queryResult;
            int pageNumber = 1;

            try {
                do {
                    query.setPage(pageNumber);
                    queryResult = twitter.search(query);
                    Iterator<Tweet> it = queryResult.getTweets().iterator();
                    resultTweets.addAll(queryResult.getTweets());
                    pageNumber++;
                } while (ISSUANCE_SIZE > resultTweets.size());
            } catch (TwitterException e) {
            }

            Iterator<Tweet> it = resultTweets.iterator();
            while (it.hasNext()) {
                Tweet next = it.next();
                if (StringChecker.hasTerm(dictionary, next.getText())) {
                    String str = next.getText();
                    int index = next.getText().indexOf("http");
                    if (index >= 0) {
                        str = str.substring(0, index);
                    }
                    Timestamp articleTimestamp = new Timestamp(next.getCreatedAt().getTime());
                    articleProvider.writeArticleToDataStore(new Article(-1, b.getId(), 2,
                            "", "", next.getText(), articleTimestamp, 0));
                }
            }
            log.info("twitter added for brandName = " + b.getName());
        }
        log.info("twitter grabber finished succesful.");
    }
}