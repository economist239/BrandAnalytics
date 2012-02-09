package ru.brandanalyst.miner.rss;

import it.sauronsoftware.feed4j.FeedParser;
import it.sauronsoftware.feed4j.bean.Feed;
import it.sauronsoftware.feed4j.bean.FeedItem;
import org.horrabin.horrorss.RssChannelBean;
import org.horrabin.horrorss.RssImageBean;
import org.horrabin.horrorss.RssItemBean;
import org.horrabin.horrorss.RssParser;
import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.util.Batch;
import ru.brandanalyst.miner.util.StringChecker;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/30/11
 * Time: 10:18 AM
 */

@Deprecated
public class RssParser2 extends AbstractRssParser {
    public RssParser2(ProvidersHandler providersHandler) {
        super(providersHandler);
    }

    @Override
    public void parse(String url, long sourceId, Batch<Article> batch) throws Exception {
        RssParser rss = new RssParser(url);
        rss.parse();
        Vector items = rss.getItems(); //Obtain a Vector of item elements (RssItemBean)

        // Iterate the list items
        for (int i = 0; i < items.size(); i++) {
            RssItemBean item = (RssItemBean) items.elementAt(i); //Cast the Object from the list to RssItemBean
            String title = item.getTitle();
            String link = item.getLink();
            String description = item.getDescription();
            String date = item.getPubDate();
            List<Long> brandIds = StringChecker.hasTerm(dictionary, title);
            for (long id : brandIds) {
                batch.submit(new Article(-1, id, sourceId, title, description,
                        link, new Timestamp(0), NUM_LIKES));
            }
        }

    }
}
