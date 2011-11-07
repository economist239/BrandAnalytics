package ru.brandanalyst.miner;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import ru.brandanalyst.core.db.provider.BrandProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.miner.listener.FontankaScraperRuntimeListener;
import ru.brandanalyst.miner.util.DataTransformator;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/26/11
 * Time: 4:13 AM
 */
public class GrabberFontanka extends Grabber {
    private static final int SEARCH_DAYS=15;
    private static final Logger log = Logger.getLogger(GrabberFontanka.class);

    private static final String endURL = "/news.html";
    private static final String sourceURL = "http://fontanka.ru";
    private static final String appendix = "/fontanka/";

    @Override
    public void setConfig(String config) {
        this.config = config;
    }

    @Override
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void grab(Date timeLimit) {
        GregorianCalendar time=new GregorianCalendar();
            for (int i=0;i<SEARCH_DAYS;i++)
            {
                StringBuilder resultURL=new StringBuilder();
                resultURL.append(sourceURL);
                resultURL.append(appendix);
                resultURL.append(time.get(GregorianCalendar.YEAR));
                resultURL.append("/");
                resultURL.append(time.get(GregorianCalendar.MONTH) + 1);
                resultURL.append("/");
                resultURL.append(time.get(GregorianCalendar.DAY_OF_MONTH));
                resultURL.append(endURL);
                StringBuilder date=new StringBuilder();
                date.append(time.get(GregorianCalendar.DAY_OF_MONTH));
            try {
                ScraperConfiguration config = new ScraperConfiguration(this.config);
                Scraper scraper = new Scraper(config, ".");
                scraper.addRuntimeListener(new FontankaScraperRuntimeListener(jdbcTemplate, timeLimit));
                scraper.addVariableToContext("QueryURL", resultURL.toString());
                scraper.addVariableToContext("AbsoluteURL", sourceURL);
                scraper.addVariableToContext("newsDate", time.getTime().toString());
                scraper.execute();
                log.info("successful processing url "+resultURL);
            } catch (Exception exception) {
                exception.printStackTrace();
                log.error("cannot process Fontanka url " + resultURL);
            }
        time.set(GregorianCalendar.DAY_OF_YEAR,time.get(GregorianCalendar.DAY_OF_YEAR)-1);
        log.info("Fontanka: succecsful");
        }
    }
}
