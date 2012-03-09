import com.thoughtworks.xstream.XStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.brandanalyst.core.util.test.AbstractTest;
import ru.brandanalyst.miner.twitter.ITwitter;
import ru.brandanalyst.miner.twitter.ITwitterException;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static ru.brandanalyst.miner.twitter.ITwitter.TwitterResultItem;


/**
 * @author Vanslov Evgeny (evans@yandex-team.ru)
 */
public class TwitterTest extends AbstractTest {
    private static final Log log = LogFactory.getLog(TwitterTest.class);

    protected ITwitter twitter;

    private String configLocations[] = {"test-config.xml"};

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }

    private static class XmlBrandDictionaryItem {
        public String brandName;
        public Set<String> aliases = new HashSet<String>();
    }

    public void testTwitter() throws ITwitterException, IOException {

        XStream xStream = new XStream();
        xStream.alias("item", XmlBrandDictionaryItem.class);

        List<XmlBrandDictionaryItem> companies = (List<XmlBrandDictionaryItem>) xStream.fromXML(new FileInputStream("tasker/src/test/brandItems.xml"));

        List<String> words = (List<String>) xStream.fromXML(new FileInputStream("tasker/src/test/words.xml"));
        Map<String, List<TwitterResultItem>> map = new HashMap<String, List<TwitterResultItem>>();
        for (XmlBrandDictionaryItem company : companies) {
            List<TwitterResultItem> results = getTweets(words, company);
            map.put(company.brandName, results);
        }
        xStream.toXML(map, new FileWriter("weeklytopsytweets.xml"));
    }

    private List<TwitterResultItem> getTweets(List<String> words, XmlBrandDictionaryItem company) throws ITwitterException {
        List<TwitterResultItem> results = new ArrayList<TwitterResultItem>();
        System.out.println("Company now: " + company.brandName);
        for (String word : words) {
            TwitterResultItem topsyItem = twitter.getPopularTweets(company.brandName + " " + word);
            results.add(topsyItem);

        }
        for (String alias : company.aliases) {
            for (String word : words) {
                TwitterResultItem topsyItem = twitter.getPopularTweets(alias + " " + word);
                results.add(topsyItem);
            }
        }
        return results;
    }
}
