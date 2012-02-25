package ru.brandanalyst.miner.twitter.impl;

import org.apache.log4j.Logger;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.miner.twitter.ITwitter;
import ru.brandanalyst.miner.twitter.ITwitterException;
import twitter4j.*;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Vanslov Evgeny (evans@yandex-team.ru)
 */
public class SimpleTwitter implements ITwitter {
    private static final Logger log = Logger.getLogger(SimpleTwitter.class);

    private static final int PAGE_SIZE = 100;
    private final int maxCount;

    public static final int DEFAULT_MAX_COUNT = 150;

    public SimpleTwitter() {
        this(DEFAULT_MAX_COUNT);
    }

    public SimpleTwitter(int maxCount) {
        this.maxCount = maxCount;
    }

    @Override
    public TwitterResultItem getPopularTweets(String queryString) throws ITwitterException {
        Twitter twitter = new TwitterFactory().getInstance();

        Query query = new Query(queryString);
        query.setRpp(PAGE_SIZE);
        query.setLang("ru");
        query.setResultType(Query.MIXED);

        Collection<Tweet> tweets = new LinkedHashSet<Tweet> ();
        QueryResult queryResult;
        int pageNumber = 1;

        try {
            do {
                query.setPage(pageNumber);
                queryResult = twitter.search(query);
                tweets.addAll(queryResult.getTweets());
                pageNumber++;
                log.info(pageNumber);
            } while (maxCount > tweets.size());
        } catch (TwitterException e) {
            throw new ITwitterException("", e);
        }
        log.info("tweets in dai11y: " + tweets.size());

        TwitterResultItem twitterResultItem = new TwitterResultItem(queryString);
        for(Tweet tweet : tweets){
            twitterResultItem.tweets.add(tweet.getText());
        }
        return twitterResultItem;
    }
}
