package ru.brandanalyst.miner.rss;

import org.apache.log4j.Logger;
import ru.brandanalyst.core.db.provider.interfaces.ArticleProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.InfoSource;
import ru.brandanalyst.core.util.Batch;
import ru.brandanalyst.miner.AbstractGrabberTask;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author OlegPan
 *         This class defines what rss channels will be used for information retrieving
 */
public class ParserStarter extends AbstractGrabberTask {
    private static final int POOL_SIZE = 10;
    private static final Logger log = Logger.getLogger(ParserStarter.class);

    protected void grab() {
        AbstractRssParser.setDictionary(handler.getBrandDictionaryProvider().getDictionary());
        List<InfoSource> infoSources = handler.getInformationSourceProvider().getAllInfoSources();
        final ArticleProvider articleProvider = handler.getArticleProvider();
        Batch<Article> batch = new Batch<Article>() {
            @Override
            public void handle(List<Article> articles) {
                articleProvider.writeListOfArticlesToDataStore(articles);
            }
        };

        log.info("begin parsing rss");

        ExecutorService service = Executors.newFixedThreadPool(POOL_SIZE);

        for (InfoSource infoSource : infoSources) {
            if (infoSource.getSphereId() != 1) continue;
            String rssSource = infoSource.getRssSource();
            if (rssSource.isEmpty()) continue;
            service.submit(new HorrorParser(rssSource, infoSource.getId(), batch));
        }

        log.info("End parsing rss");
        batch.flush();
    }
}
