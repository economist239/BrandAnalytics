package ru.brandanalyst.miner.twitter;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author Vanslov Evgeny (evans@yandex-team.ru)
 */

public interface ITwitter {
    TwitterResultItem getPopularTweets(String query) throws ITwitterException;
    public class TwitterResultItem {
        public final String query;
        public final Collection<String> tweets = new LinkedHashSet<String>();

        public TwitterResultItem(String query) {
            this.query = query;
        }
    }

}
