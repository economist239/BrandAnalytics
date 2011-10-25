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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Александр Сенов
 * Date: 23.10.11
 * Time: 11:10
 */
public class RiaNewsScraperRuntimeListener implements ScraperRuntimeListener {

    int i = 0;
    private static final Logger log = Logger.getLogger(RiaNewsScraperRuntimeListener.class);

    protected SimpleJdbcTemplate jdbcTemplate;
    protected ArticleProvider articleProvider;

    public RiaNewsScraperRuntimeListener(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        articleProvider = new ArticleProvider(jdbcTemplate);
    }

    private Timestamp evalTimestamp(String stringDate) {
        stringDate = stringDate.replace("\n", "");
        stringDate = stringDate.replace(" ", "");
        int nano = 0;
        int second = 0;
        int minute = Integer.parseInt(stringDate.substring(13, 15));
        int hour = Integer.parseInt(stringDate.substring(10, 12));
        int day = Integer.parseInt(stringDate.substring(0, 2));
        int month = Integer.parseInt(stringDate.substring(3, 5));
        int year = Integer.parseInt(stringDate.substring(6, 10));

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day,hour,minute);
        Timestamp timestamp = new Timestamp(calendar.getTime().getTime());

        return timestamp;
    }

    public void onExecutionStart(Scraper scraper) {
    }

    public void onExecutionPaused(Scraper scraper) {
    }

    public void onExecutionContinued(Scraper scraper) {
    }

    public void onNewProcessorExecution(Scraper scraper, BaseProcessor baseProcessor) {
        if ("body".equalsIgnoreCase(baseProcessor.getElementDef().getShortElementName())) {
            String oneNew = scraper.getContext().get("oneNew").toString();
            if (oneNew.substring(1, 7).equals("resume")) {
                scraper.addVariableToContext("isUnusualNewsCite", 1);
            } else {
                scraper.addVariableToContext("isUnusualNewsCite", 0);
            }
        }
    }

    public void onExecutionEnd(Scraper scraper) {

    }

    private String clearString(String text) {
        int point = text.indexOf('.');
        text = text.substring(point + 1);

        int beginIndex = text.indexOf("Добавить видео в блог");
        if (beginIndex >= 0) {
            int endIndex = text.substring(beginIndex + 1).indexOf("Добавить видео в блог");
            return text.replace(text.substring(beginIndex, beginIndex + endIndex + 36), "");
        } else {
            return text;
        }
    }

    public void onProcessorExecutionFinished(Scraper scraper, BaseProcessor baseProcessor, Map map) {
        if ("body".equalsIgnoreCase(baseProcessor.getElementDef().getShortElementName())) {

            Variable newsTitle = (Variable) scraper.getContext().get("newsTitle");
            Variable newsText = (Variable) scraper.getContext().get("newsFullText");
            Variable newsDate = (Variable) scraper.getContext().get("newsDate");
            long brandId = ((Variable) scraper.getContext().get("brandId")).toLong();
            Timestamp articleTimestamp = evalTimestamp(newsDate.toString());
            String articleContent = DataTransformator.clearString(newsText.toString());
            articleContent = clearString(articleContent);
            String articleTitle = newsTitle.toString();
            String articleLink = scraper.getContext().get("riaAbsoluteURL").toString() + scraper.getContext().get("oneNew").toString();
            Article article = new Article(-1, brandId, 6, articleTitle, articleContent, articleLink, articleTimestamp, 0);

            System.out.println(i++);
            articleProvider.writeArticleToDataStore(article);
            log.info("RIA: article added...");
        }
        if ("empty".equalsIgnoreCase(baseProcessor.getElementDef().getShortElementName())) {

            String tempNewsDate = scraper.getContext().get("tempNewsDate").toString();
            if (tempNewsDate.replace(" ", "").equals("")) {
                scraper.addVariableToContext("isReallyUnusualNewsCite", 1);
            } else {
                scraper.addVariableToContext("isReallyUnusualNewsCite", 0);
            }
        }
    }

    public void onExecutionError(Scraper scraper, Exception e) {
        if (e != null) {
            log.error("RiaNewsScraperRuntimeListener error");
        }
    }
}
