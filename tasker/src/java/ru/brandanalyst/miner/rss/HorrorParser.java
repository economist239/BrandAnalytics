package ru.brandanalyst.miner.rss;

import org.horrabin.horrorss.RssItemBean;
import org.horrabin.horrorss.RssParser;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.util.Batch;
import ru.brandanalyst.miner.util.StringChecker;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    DateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    public HorrorParser(String url, long sourceId, Batch<Article> batch) {
        super(url, sourceId, batch);
    }

    @Override
    protected void parse() throws Exception {
        try {
            RssParser rss = new RssParser(url);
            rss.parse();
            Vector items = rss.getItems();

            for (int i = 0; i < items.size(); i++) {
                RssItemBean item = (RssItemBean) items.elementAt(i); 
                String title = item.getTitle();
                List<Long> brandIds = StringChecker.hasTerm(dictionary, title);
                if (!brandIds.isEmpty()) {
                    String link = item.getLink();
                    String desc = item.getDescription();
                    String date = item.getPubDate();
                    for (Long id : brandIds) {
                        batch.submit(new Article(-1, id, title, desc, link, new Timestamp(FORMATTER.parse(date).getTime()), NUM_LIKES));
                    }
                }
            }
        } catch (Exception e) {
            parse();
        }
    }
}
