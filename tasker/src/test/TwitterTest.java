import com.thoughtworks.xstream.XStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.brandanalyst.miner.twitter.ITwitter;
import ru.brandanalyst.miner.twitter.ITwitterException;
import ru.brandanalyst.miner.twitter.impl.SimpleTwitter;
import ru.brandanalyst.miner.twitter.impl.TopsyTwitter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vanslov Evgeny (evans@yandex-team.ru)
 */
public class TwitterTest {
    private static final Log log = LogFactory.getLog(TwitterTest.class);

    public static void main(String[] args) throws ITwitterException, IOException {
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
        List<ITwitter.TwitterResultItem> companiesTweets = new ArrayList<ITwitter.TwitterResultItem>();
        for (String company : companies) {
            log.info("Company now: " + company);
            ITwitter.TwitterResultItem topsyItem = new SimpleTwitter().getPopularTweets(company);
            companiesTweets.add(topsyItem);
            log.info("Company done; " + company + " Found: " + topsyItem.tweets.size());
        }
        log.info("Mining topsy ended");

        XStream xStream = new XStream();
        xStream.toXML(companiesTweets, new FileWriter("tweets.xml"));
    }
}
