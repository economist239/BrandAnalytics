package ru.brandanalyst.miner.rss;

import org.apache.log4j.Logger;
import ru.brandanalyst.core.db.provider.interfaces.ArticleProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.InfoSource;
import ru.brandanalyst.core.util.Batch;
import ru.brandanalyst.miner.grabbers.Grabber;

import java.util.Date;
import java.util.List;

/**
 * @author OlegPan
 *         This class defines what rss channels will be used for information retrieving
 */
public class ParserStarter extends Grabber {
    private static final Logger log = Logger.getLogger(ParserStarter.class);

    public void grab(Date date) {
        AbstractRssParser parser = new RssParser(handler);
        List<InfoSource> infoSources = handler.getInformationSourceProvider().getAllInfoSources();
        final ArticleProvider articleProvider = handler.getArticleProvider();
        Batch<Article> batch = new Batch<Article>() {
            @Override
            public void handle(List<Article> articles) {
                articleProvider.writeListOfArticlesToDataStore(articles);
            }
        };

        log.info("begin parsing rss");

        for (InfoSource infoSource : infoSources) {
            if (infoSource.getSphereId() != 1) continue;
            String rssSource = infoSource.getRssSource();
            if (rssSource.isEmpty()) continue;
            log.info("Parse rss " + rssSource);
            parser.parse(rssSource, infoSource.getId(), batch);
        }

        log.info("End parsing rss");
        batch.flush();
    }
}
