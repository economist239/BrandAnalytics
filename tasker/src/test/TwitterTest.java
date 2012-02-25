import com.thoughtworks.xstream.XStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.brandanalyst.AbstractTest;
import ru.brandanalyst.miner.twitter.ITwitter;
import ru.brandanalyst.miner.twitter.ITwitterException;
import ru.brandanalyst.miner.twitter.impl.SimpleTwitter;
import ru.brandanalyst.miner.twitter.impl.TopsyTwitter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.brandanalyst.miner.twitter.ITwitter.TwitterResultItem;


/**
 * @author Vanslov Evgeny (evans@yandex-team.ru)
 */
public class TwitterTest extends AbstractTest{
    private static final Log log = LogFactory.getLog(TwitterTest.class);

    protected ITwitter twitter;

    private String configLocations[] = {"test-config.xml"};
    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
    public void testTwitter() throws ITwitterException, IOException {
        String companies[] = {
                "google",
                /*"Сбербанк",
                "Газпром",
                "ВТБ",
                "Роснефть",
                "ЛУКОЙЛ",
                "НорНикель",
                "РусГидро",
                "НЛМК",
                "НОВАТЭК",
                "Сургутнефтегаз",
                "Татнефть",
                "МТС",
                "Полюс-Золото",
                "Полиметалл",
                "Газпрнефть",
                "Акрон",
                "Автоваз",
                "Соллерс",
                "КАМАЗ",
                "ОГК - 3"*/
        };

        log.info("Mining topsy started");
        List<TwitterResultItem> companiesTweets = new ArrayList<TwitterResultItem>();
        for (String company : companies) {
            log.info("Company now: " + company);
            TwitterResultItem topsyItem = twitter.getPopularTweets(company);
            companiesTweets.add(topsyItem);
            log.info("Company done; " + company + " Found: " + topsyItem.tweets.size());
        }
        log.info("Mining topsy ended");

        XStream xStream = new XStream();
        xStream.toXML(companiesTweets, new FileWriter("tweets.xml"));
    }
}
