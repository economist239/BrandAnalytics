package ru.brandanalyst.miner;

import org.apache.log4j.Logger;
import ru.brandanalyst.core.db.provider.ArticleProvider;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.Query;
import twitter4j.Tweet;
import twitter4j.QueryResult;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import ru.brandanalyst.core.db.provider.BrandProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.Article;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * Created by IntelliJ IDEA.
 * User: Александр Сенов
 * Date: 10.10.11
 * Time: 14:14
 */
public class GrabberTwitter extends Grabber {
    private static final Logger log = Logger.getLogger(GrabberTwitter.class);

    private static final int ISSUANCE_SIZE = 100;

    public void setConfig(String config) {
        this.config = config;  //not using
    }

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void grab(Date timeLimit) {

        Twitter twitter = new TwitterFactory().getInstance();

        List<Brand> brandList = new BrandProvider(jdbcTemplate).getAllBrands();
        ArticleProvider articleProvider = new ArticleProvider(jdbcTemplate);

        for (Brand b : brandList) {

            try {
                //TODO: make this piece of shit, named as code, BETTER (get rid of <code>pageNumber</code>, for example)
                Query query = new Query(b.getName());
                query.setRpp(ISSUANCE_SIZE);
                //Calendar cal = new GregorianCalendar();
                query.setSince("2011" + "-" + "01" + "-" + "01");
                //query.setSince(new SimpleDateFormat("yyy-MM-dd").format(cal.getTime()));
                query.setLang("ru");
                //query.setRpp(100); // it's ok

                List<Tweet> resultTweets;
                QueryResult queryResult;
                int pageNumber = 1;
                do {
                    query.setPage(pageNumber);
                    queryResult = twitter.search(query);
                    resultTweets = queryResult.getTweets();
                    for (int i = 0; i < resultTweets.size(); ++i) {
                        Timestamp articleTimestamp = new Timestamp(resultTweets.get(i).getCreatedAt().getTime());
                        articleProvider.writeArticleToDataStore(new Article(-1, b.getId(), 2,
                                "", "", resultTweets.get(i).getText(), articleTimestamp, 0));
                    }
                    log.info("twitter added for brandId = " + Long.toString(b.getId()));
                    pageNumber++;

                } while (ISSUANCE_SIZE == resultTweets.size());
            } catch (Exception e) {
                log.error("cannot process twitter. brandId = " + Long.toString(b.getId()));
            }
        }
    }
}
