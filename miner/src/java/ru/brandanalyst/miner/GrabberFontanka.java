package ru.brandanalyst.miner;

import org.apache.log4j.Logger;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import ru.brandanalyst.miner.listener.FontankaScraperRuntimeListener;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/26/11
 * Time: 4:13 AM
 */
public class GrabberFontanka extends Grabber {
    private static final int SEARCH_DAYS = 30;
    private static final Logger log = Logger.getLogger(GrabberFontanka.class);

    private static final String endURL = "/news.html";
    private static final String sourceURL = "http://fontanka.ru";
    private static final String appendix = "/fontanka/";

    @Override
    public void grab(Date timeLimit) {
        GregorianCalendar time = new GregorianCalendar();
        for (int i = 0; i < SEARCH_DAYS; i++) {
            StringBuilder resultURL = new StringBuilder();
            resultURL.append(sourceURL);
            resultURL.append(appendix);
            resultURL.append(time.get(GregorianCalendar.YEAR));
            resultURL.append("/");
            resultURL.append(time.get(GregorianCalendar.MONTH) + 1);
            resultURL.append("/");
            resultURL.append(time.get(GregorianCalendar.DAY_OF_MONTH));
            resultURL.append(endURL);

            try {
                ScraperConfiguration config = new ScraperConfiguration(this.config);
                Scraper scraper = new Scraper(config, ".");
                scraper.addRuntimeListener(new FontankaScraperRuntimeListener(dirtyProvidersHandler, timeLimit));
                scraper.addVariableToContext("QueryURL", resultURL.toString());
                scraper.addVariableToContext("AbsoluteURL", sourceURL);
                scraper.execute();
                log.info("successful processing url " + resultURL);
            } catch (Exception exception) {
                exception.printStackTrace();
                log.error("cannot process Fontanka url " + resultURL);
            }
            time.set(GregorianCalendar.DAY_OF_YEAR, time.get(GregorianCalendar.DAY_OF_YEAR) - 1);
            log.info("Fontanka: succecsful");
        }
    }
}
