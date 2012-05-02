package ru.brandanalyst.miner.rss;

import org.apache.commons.lang.StringUtils;
import org.horrabin.horrorss.RssItemBean;
import org.horrabin.horrorss.RssParser;
import org.joda.time.LocalDateTime;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.util.Batch;
import ru.brandanalyst.core.util.BrandAnalyticsLocale;
import ru.brandanalyst.core.util.SimpleTextCleaner;
import ru.brandanalyst.miner.util.StringChecker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/9/12
 * Time: 5:07 PM
 */
public class HorrorParser extends AbstractRssParser {
    private static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    private static final DateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT, BrandAnalyticsLocale.RU);

    private long maxCount = 15;
    private static final long SLEEP_TIME = 10000;

    public HorrorParser(final String url, final long sourceId, final Batch<Article> batch) {
        super(url, sourceId, batch);
    }

    private static int i = 0;

    @Override
    protected void parse() throws Exception {
        try {
            RssParser rss = new RssParser(url);
            rss.parse();
            for (RssItemBean item : (List<RssItemBean>) rss.getItems()) {
                String title = item.getTitle();
                Collection<Long> brandIds = StringChecker.hasTerm(dictionary, title);
                if (!brandIds.isEmpty()) {
                    String link = item.getLink();
                    String desc = item.getDescription();
                    String date = item.getPubDate();
                    for (Long id : brandIds) {
                        try {
                            final Article article = new Article(id, sourceId, SimpleTextCleaner.clean(title), SimpleTextCleaner.clean(desc), link, new LocalDateTime(FORMATTER.parse(date)), NUM_LIKES);
                            if (article.getContent() != null && article.getTitle() != null && !(article.getContent().isEmpty()) && !(article.getTitle().isEmpty())) {
                                log.debug("Article added: " + title);
                                batch.submit(article);
                            }
                        } catch (ParseException ignored) {
                            log.error(ignored);
                        }
                    }
                }
            }
            log.debug("rss parsed" + i++);
        } catch (Exception e) {
            log.warn(url);
            if (maxCount-- > 0) {
                Thread.sleep(SLEEP_TIME);
                parse();
            }
        }
    }
}
