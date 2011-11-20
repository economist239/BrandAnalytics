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

import static ru.brandanalyst.core.time.TimeProperties.SINGLE_DAY;

/**
 * Created by IntelliJ IDEA.
 * User: Александр Сенов
 * Date: 10.10.11
 * Time: 14:14
 */
public class GrabberTwitter extends Grabber {
    private static final long TIME_LIMIT = (long) 10;

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


        for (Brand b : brandList) {
            BrandDictionaryItem dictionary = dictionaryProvider.getDictionaryItem(b.getId());

            for (int i = 0; i < 50; i++) {
                log.info("days ago: " + i + " brand:" + b.getName());

                String untilTimeLimit = dateFormat.format(new Date().getTime() - ((long) i) * SINGLE_DAY);
                String sinceTimeLimit = dateFormat.format(new Date().getTime() - ((long) i + 1) * SINGLE_DAY);

                Query query = new Query(b.getName());
                query.setRpp(PAGE_SIZE);

                query.setSince(sinceTimeLimit);
                query.setUntil(untilTimeLimit);
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
                    } while (ISSUANCE_SIZE > resultTweets.size());
                } catch (TwitterException e) {
                    log.info("tweets in day: " + resultTweets.size());
                    //    e.printStackTrace();
                }

                Iterator<Map.Entry<String, TweetInfo>> resultIterator =
                        removeDuplicates(resultTweets, dictionary).entrySet().iterator();
                while (resultIterator.hasNext()) {
                    Map.Entry<String, TweetInfo> next = resultIterator.next();
                    articleProvider.writeArticleToDataStore(new Article(-1, b.getId(), 2,
                            "", "", next.getKey(), getSimpleTime(next.getValue().getTime()), next.getValue().getNumLikes()));
                }

            }
            log.info("twitter added for brandName = " + b.getName());
        }
        log.info("twitter grabber finished succesful.");
    }

    private Timestamp getSimpleTime(Date date) {
        return new Timestamp((new Date(date.getYear(), date.getMonth(), date.getDay())).getTime());
    }

    //bad style?   - very bad
    private Map<String, TweetInfo> removeDuplicates(List<Tweet> resultTweets, BrandDictionaryItem dictionary) {
        Map<String, TweetInfo> tweetsInfoMap = new HashMap<String, TweetInfo>();
        Iterator<Tweet> it = resultTweets.iterator();
        while (it.hasNext()) {
            Tweet next = it.next();
            if (StringChecker.hasTerm(dictionary, next.getText())) {
                String str = next.getText().trim();
                int index = next.getText().indexOf("http");
                if (index >= 0) {
                    str = str.substring(0, index);
                }
                if (tweetsInfoMap.containsKey(str)) {
                    TweetInfo tweetInfo = tweetsInfoMap.get(str);
                    tweetInfo.setNumLikes(tweetInfo.getNumLikes() + 1);
                    tweetsInfoMap.put(str, tweetInfo);
                } else {
                    TweetInfo tweetInfo
                            = new TweetInfo(new Timestamp(next.getCreatedAt().getTime()), 1);
                    tweetsInfoMap.put(str, tweetInfo);
                }

            }
        }
        return tweetsInfoMap;
    }

    class TweetInfo {
        Timestamp time;
        int numLikes;

        TweetInfo(Timestamp time, int numLikes) {
            this.numLikes = numLikes;
            this.time = time;
        }

        public Timestamp getTime() {
            return time;
        }

        public void setTime(Timestamp time) {
            this.time = time;
        }

        public int getNumLikes() {
            return numLikes;
        }

        public void setNumLikes(int numLikes) {
            this.numLikes = numLikes;
        }
    }
}
