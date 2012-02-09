package ru.brandanalyst.miner.grabbers;

import org.apache.log4j.Logger;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import ru.brandanalyst.miner.grabbers.listener.WikipediaScraperRuntimeListener;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/26/11
 * Time: 10:02 AM
 */
public class GrabberWikipedia extends Grabber {
    private static final Logger log = Logger.getLogger(GrabberWikipedia.class);

    @Override
    public void grab(Date timeLimit) {
        try {
            String linksFile = config.substring(0, config.indexOf(';'));
            String configFile = config.substring(config.indexOf(';') + 1);
            ScraperConfiguration config = new ScraperConfiguration(configFile);
            Scraper scraper = new Scraper(config, ".");
            scraper.addRuntimeListener(new WikipediaScraperRuntimeListener(handler));
            scraper.addVariableToContext("inputFile", linksFile);
            scraper.execute();
            log.info("Wikipedia: succecsful");
        } catch (Exception e) {
            log.error("cannot process Wikipedia", e);
        }
    }
}
