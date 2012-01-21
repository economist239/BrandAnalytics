package ru.brandanalyst.miner.grabbers.listener;

import org.apache.log4j.Logger;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperRuntimeListener;
import org.webharvest.runtime.processors.BaseProcessor;
import org.webharvest.runtime.variables.Variable;
import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.db.provider.interfaces.ArticleProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.BrandDictionaryItem;
import ru.brandanalyst.miner.util.DataTransformator;
import ru.brandanalyst.miner.util.StringChecker;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/26/11
 * Time: 4:14 AM
 */
public class FontankaScraperRuntimeListener implements ScraperRuntimeListener {

    private int i = 0;
    private static final Logger log = Logger.getLogger(FontankaScraperRuntimeListener.class);

    private ArticleProvider articleProvider;
    private List<BrandDictionaryItem> dictionary;
    private Date timeLimit;

    public FontankaScraperRuntimeListener(ProvidersHandler providersHandler, Date timeLimit) {
        this.timeLimit = timeLimit;
        articleProvider = providersHandler.getArticleProvider();
        providersHandler.getBrandDictionaryProvider().getDictionary();
    }

    private Timestamp evalTimestamp(String stringDate) throws StringIndexOutOfBoundsException {
        stringDate = stringDate.replace("\n", "");
        stringDate = stringDate.replace(" ", "");
        stringDate = stringDate.substring(0, 10);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date;
        try {
            date = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            date = new Date();
        }

        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

    public void onExecutionStart(Scraper scraper) {
    }

    public void onExecutionPaused(Scraper scraper) {
    }

    public void onExecutionContinued(Scraper scraper) {
    }

    public void onNewProcessorExecution(Scraper scraper, BaseProcessor baseProcessor) {

    }

    public void onExecutionEnd(Scraper scraper) {

    }

    public void onProcessorExecutionFinished(Scraper scraper, BaseProcessor baseProcessor, Map map) {

        if ("body".equalsIgnoreCase(baseProcessor.getElementDef().getShortElementName())) {
            try {
                Variable newsTitle = (Variable) scraper.getContext().get("newsTitle");
                List<Long> brandIds = StringChecker.hasTerm(dictionary, newsTitle.toString());
                if (brandIds.isEmpty()) return;

                Variable newsText = (Variable) scraper.getContext().get("newsFullText");
                Variable newsDate = (Variable) scraper.getContext().get("newsDate");
                Timestamp articleTimestamp = evalTimestamp(newsDate.toString());
                if (articleTimestamp.getTime() < timeLimit.getTime()) {
                    scraper.stopExecution();
                }

                String articleContent = DataTransformator.clearString(newsText.toString());
                String articleTitle = newsTitle.toString();
                String articleLink = scraper.getContext().get("AbsoluteURL").toString() + scraper.getContext().get("oneNew").toString();
                for (Long brandId : brandIds) {
                    Article article = new Article(-1, brandId, 10, articleTitle, articleContent, articleLink, articleTimestamp, 0);
                    articleProvider.writeArticleToDataStore(article);
                }
                log.info("Fontanka: " + ++i + " article added... title = " + articleTitle);
            } catch (Exception e) {

            }
        }
    }

    public void onExecutionError(Scraper scraper, Exception e) {
        if (e != null) {
            log.error("FontankaNewsScraperRuntimeListener error");
        }
    }
}
