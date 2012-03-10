package ru.brandanalyst.miner.rss;

import org.horrabin.horrorss.RssItemBean;
import org.horrabin.horrorss.RssParser;
import org.joda.time.LocalDateTime;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.util.Batch;
import ru.brandanalyst.core.util.Time;
import ru.brandanalyst.miner.util.StringChecker;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/9/12
 * Time: 5:07 PM
 */
public class HorrorParser extends AbstractRssParser {
    public static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    private static final DateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    private long maxCount = 15;
    private static final long SLEEP_TIME = 10000;

    public HorrorParser(String url, long sourceId, Batch<Article> batch) {
        super(url, sourceId, batch);
    }

    private static int i = 0;
    @Override
    protected void parse() throws Exception {
        try {
            RssParser rss = new RssParser(url);
            rss.parse();
            log.debug("rss parsed" + i++);
            for (RssItemBean item : (List<RssItemBean>) rss.getItems()) {
                String title = item.getTitle();
                Collection<Long> brandIds = StringChecker.hasTerm(dictionary, title);
                if (!brandIds.isEmpty()) {
                    String link = item.getLink();
                    String desc = item.getDescription();
                    String date = item.getPubDate();
                    for (Long id : brandIds) {
                        log.debug("Article added: " + title);
                        batch.submit(new Article(id, sourceId, title, desc, link, new LocalDateTime(FORMATTER.parse(date)), NUM_LIKES));
                    }
                }
            }
        } catch (Exception e) {
            log.error(url, e);
            if(maxCount-- > 0){
                Thread.sleep(SLEEP_TIME);
                parse();
            }
        }
    }
}
