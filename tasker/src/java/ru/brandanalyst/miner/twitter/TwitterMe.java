package ru.brandanalyst.miner.twitter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author Vanslov Evgeny (evans@yandex-team.ru)
 */
public class TwitterMe {
    private static final Log log = LogFactory.getLog(TwitterMe.class);

    public static void main(String args[]) throws Exception {
        AccessToken accessToken = new AccessToken("278965309-z5tBPD5OynnvQRRgQyqN2srbfOtc3lf5qWUdFCQj", "lk1WnFq6Xp7zFlAk4XWfyWZqIMJ0oPf5jRfWlYLXXAw");
        Twitter twitter = new TwitterFactory().getInstance(accessToken);
//        twitter.setOAuthConsumer("EwJR8447Z1pgHZ4CDjQ", "B7AOkbuUvXUj6r64uWassVA58fxIekBnvRKVFe2U");

        String queryString = "google";
        Query query = new Query(queryString);
        query.setRpp(100);
        query.setLang("ru");
        query.setResultType(Query.MIXED);

        query.setPage(1);
        QueryResult queryResult = twitter.search(query);
        log.info(queryResult.getTweets());
    }

}
