package ru.brandanalyst.miner.rss;

import org.junit.Assert;
import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.BrandDictionaryItem;
import ru.brandanalyst.core.util.Batch;
import ru.brandanalyst.core.util.test.AbstractTest;

import java.util.List;

/**
 * User: dima
 * Date: 3/9/12
 * Time: 10:06 PM
 */
public class RssTest extends AbstractTest {

    protected ProvidersHandler dirtyProvidersHandler;
    protected ProvidersHandler pureProvidersHandler;
    protected ParserStarter rssGrabberTask;

    {
        configLocations = new String[] {
                "classpath:ru/brandanalyst/miner/rss/test-rss-config.xml",
                "classpath:ru/brandanalyst/db/db-test-config.xml"
        };
    }

    public void testSimpleRss() {
        rssGrabberTask.grab();
    }
    
    public void testRss() throws Exception {
        HorrorParser.setDictionary(dirtyProvidersHandler.getBrandDictionaryProvider().getDictionary());

        Batch<Article> b = new Batch<Article>() {
            @Override
            public void handle(List<Article> articles) {
                Assert.assertTrue(articles.get(0).getTitle().contains("нальность Apple iPad снизилась с выходом новой"));
            }
        };
        HorrorParser parser = new HorrorParser("./tasker/src/test/ru/brandanalyst/miner/rss/fake-rss.xml", 1, b);
        parser.parse();
        b.flush();
    }
}
