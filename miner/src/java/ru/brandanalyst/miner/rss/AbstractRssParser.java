package ru.brandanalyst.miner.rss;

import org.apache.log4j.Logger;
import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.BrandDictionaryItem;
import ru.brandanalyst.core.util.Batch;

import java.util.List;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/30/11
 * Time: 10:35 AM
 */
public abstract class AbstractRssParser {
    protected static final int NUM_LIKES = 0;
    protected static final Logger log = Logger.getLogger(AbstractRssParser.class);
    protected List<BrandDictionaryItem> dictionary;

    public AbstractRssParser(ProvidersHandler providersHandler) {
        dictionary = providersHandler.getBrandDictionaryProvider().getDictionary();
    }

    abstract void parse(String url, long sourceId, Batch<Article> batch);
}
