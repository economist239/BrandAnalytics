package ru.brandanalyst.miner.rss;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.joda.time.LocalDateTime;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.util.Batch;
import ru.brandanalyst.core.util.BrandAnalyticsLocale;
import ru.brandanalyst.core.util.SimpleTextCleaner;
import ru.brandanalyst.miner.util.StringChecker;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * User: daddy-bear
 * Date: 01.05.12
 * Time: 9:22
 */
public class RomeParser extends AbstractRssParser {
    private long maxCount = 15;
    private static final long SLEEP_TIME = 10000;

    public RomeParser(final String url, final long sourceId, final Batch<Article> batch) {
        super(url, sourceId, batch);
    }

    @Override
    protected void parse() throws Exception {
        try {

            XmlReader reader = new XmlReader(new URL(url));
            SyndFeed feed = new SyndFeedInput().build(reader);

            for (SyndEntry e : (List<SyndEntry>) feed.getEntries()) {
                String title = e.getTitle();
                Collection<Long> brandIds = StringChecker.hasTerm(dictionary, title);
                if (!brandIds.isEmpty()) {
                    String link = e.getLink();
                    String desc = (e.getDescription() == null) ? "" : e.getDescription().getValue();
                    Date date = e.getPublishedDate();
                    for (Long id : brandIds) {
                        final Article article = new Article(id, sourceId, SimpleTextCleaner.clean(title), SimpleTextCleaner.clean(desc), link, new LocalDateTime(date), NUM_LIKES);
                        if (article.getContent() != null && article.getTitle() != null && !(article.getContent().isEmpty()) && !(article.getTitle().isEmpty())) {
                            log.debug("Article added: " + article.getTitle());
                            batch.submit(article);
                        }
                    }
                }
            }
            log.info(sourceId + " - processed");
        } catch (Exception e) {
            log.warn(url);
            if (maxCount-- > 0) {
                Thread.sleep(SLEEP_TIME);
                parse();
            }
        }
    }
}
