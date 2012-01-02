package ru.brandanalyst.miner.rss;

import it.sauronsoftware.feed4j.FeedParser;
import it.sauronsoftware.feed4j.bean.Feed;
import it.sauronsoftware.feed4j.bean.FeedItem;
import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.util.Batch;
import ru.brandanalyst.miner.util.StringChecker;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/30/11
 * Time: 10:18 AM
 */

public class RssParser extends AbstractRssParser {
    public RssParser(ProvidersHandler providersHandler) {
        super(providersHandler);
    }

    @Override
    public void parse(String url, long sourceId, Batch<Article> batch) {
        try {
            Feed feed = FeedParser.parse(new URL(url));
            for (int i = 0; i < feed.getItemCount(); ++i) {
                FeedItem item = feed.getItem(i);
                String title = item.getTitle();
                List<Long> brandIds = StringChecker.hasTerm(dictionary, title);
                for (long id : brandIds) {
                    batch.submit(new Article(-1, id, sourceId, title, item.getDescriptionAsText(),
                            item.getLink().toString(), new Timestamp(item.getPubDate().getTime()), NUM_LIKES));
                }
            }
        } catch (Exception e) {
            log.error("can't process rss url: " + url, e);
            throw new RuntimeException(e);
        }
    }
}
