import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.brandanalyst.core.util.test.AbstractTest;
import ru.brandanalyst.miner.twitter.GrabberTaskTwitter;
import twitter4j.*;
import twitter4j.auth.AccessToken;

/**
 * @author Vanslov Evgeny (evans@yandex-team.ru)
 */
public class TwitterGrabberTest extends AbstractTest {
    private static final Log log = LogFactory.getLog(TwitterGrabberTest.class);
    private String configLocations[] = {"test-config.xml"};

    //protected ProvidersHandler providersHandler;

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }

    public void testTwitter() throws Exception {
        GrabberTaskTwitter grabber = new GrabberTaskTwitter();
        //grabber.setDirtyProvidersHandler(providersHandler);
        /*List<Tweet> tweets = grabber.getTweets("apple");
        for(Tweet tweet : tweets){
            log.info(tweet.getText());
        }*/

        AccessToken accessToken = new AccessToken("278965309-z5tBPD5OynnvQRRgQyqN2srbfOtc3lf5qWUdFCQj", "lk1WnFq6Xp7zFlAk4XWfyWZqIMJ0oPf5jRfWlYLXXAw");
        Twitter twitter = new TwitterFactory().getInstance(accessToken);

        /*Query query = new Query("google");
        query.setLang("en");
        query.setRpp(100);
        query.setResultType(Query.POPULAR);

        QueryResult result = twitter.search(query);
        for (Tweet tweet : result.getTweets()) {
            System.out.println(tweet.getText());
        }*/
    }
}
