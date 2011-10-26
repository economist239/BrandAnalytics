package ru.brandanalyst.miner.listener;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperRuntimeListener;
import org.webharvest.runtime.processors.BaseProcessor;
import org.webharvest.runtime.variables.Variable;
import ru.brandanalyst.core.db.provider.ArticleProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.miner.util.DataTransformator;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/26/11
 * Time: 4:14 AM
 */
public class FontankaScraperRuntimeListener implements ScraperRuntimeListener {

    //private int i = 0;
    private static final Logger log = Logger.getLogger(FontankaScraperRuntimeListener.class);

    protected SimpleJdbcTemplate jdbcTemplate;
    protected ArticleProvider articleProvider;

    public FontankaScraperRuntimeListener(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        articleProvider = new ArticleProvider(jdbcTemplate);
    }

    private Timestamp evalTimestamp(String stringDate) {
        stringDate = stringDate.replace("\n", "");
        stringDate = stringDate.replace(" ", "");

        int minute = Integer.parseInt(stringDate.substring(13, 15));
        int hour = Integer.parseInt(stringDate.substring(10, 12));
        int day = Integer.parseInt(stringDate.substring(0, 2));
        int month = Integer.parseInt(stringDate.substring(3, 5));
        int year = Integer.parseInt(stringDate.substring(6, 10));

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        return new Timestamp(calendar.getTime().getTime());
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
            Variable newsTitle = (Variable) scraper.getContext().get("newsTitle");
            Variable newsText = (Variable) scraper.getContext().get("newsFullText");
            Variable newsDate = (Variable) scraper.getContext().get("newsDate");
            long brandId = ((Variable) scraper.getContext().get("brandId")).toLong();
            Timestamp articleTimestamp = evalTimestamp(newsDate.toString());

            String articleContent = DataTransformator.clearString(newsText.toString());
            System.out.println(articleContent);
            String articleTitle = newsTitle.toString();
            String articleLink = scraper.getContext().get("AbsoluteURL").toString() + scraper.getContext().get("oneNew").toString();
            Article article = new Article(-1, brandId, 10, articleTitle, articleContent, articleLink, articleTimestamp, 0);

            //System.out.println(i++);
            articleProvider.writeArticleToDataStore(article);
            log.info("Fontanka: article added...");
        }
    }

    public void onExecutionError(Scraper scraper, Exception e) {
        if (e != null) {
            log.error("FontankaNewsScraperRuntimeListener error");
        }
    }
}
