package ru.brandanalyst.miner.rss;

import org.apache.log4j.Logger;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.BrandDictionaryItem;
import ru.brandanalyst.core.util.Batch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/30/11
 * Time: 10:35 AM
 */
public abstract class AbstractRssParser implements Runnable {
    protected static final int NUM_LIKES = 0;
    protected static final Logger log = Logger.getLogger(AbstractRssParser.class);
    protected static List<BrandDictionaryItem> dictionary = new ArrayList<BrandDictionaryItem>();
    protected final String url;
    protected final long sourceId;
    protected final Batch<Article> batch;

    public static void setDictionary(List<BrandDictionaryItem> dictionary) {
        AbstractRssParser.dictionary = dictionary;
    }

    public AbstractRssParser(String url, long sourceId, Batch<Article> batch) {
        this.url = url;
        this.sourceId = sourceId;
        this.batch = batch;
    }

    protected abstract void parse() throws Exception;

    @Override
    public void run() {
        try {
            parse();
        } catch (Exception e) {
            log.error("error while processing " + url);
        }
    }
}
