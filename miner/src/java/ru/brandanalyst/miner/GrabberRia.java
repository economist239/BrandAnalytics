package ru.brandanalyst.miner;

import org.apache.log4j.Logger;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.miner.listener.RiaNewsScraperRuntimeListener;
import ru.brandanalyst.miner.util.DataTransformator;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Senov
 * Date: 23.10.11
 * Time: 11:29
 */
public class GrabberRia extends Grabber {

    private static final Logger log = Logger.getLogger(GrabberRia.class);

    private static final String searchURL = "http://ria.ru/search/?query=";
    private static final String sourceURL = "http://ria.ru";

    @Override
    public void grab(Date timeLimit) {
        for (Brand b : dirtyProvidersHandler.getBrandProvider().getAllBrands()) {
            try {
                ScraperConfiguration config = new ScraperConfiguration(this.config);
                Scraper scraper = new Scraper(config, ".");
                scraper.setDebug(true);
                scraper.addRuntimeListener(new RiaNewsScraperRuntimeListener(dirtyProvidersHandler, timeLimit));
                String query = DataTransformator.stringToQueryString(b.getName());
                scraper.addVariableToContext("riaQueryURL", searchURL + query + "&p="); //"$p" - suffix for result page number
                scraper.addVariableToContext("riaAbsoluteURL", sourceURL);
                scraper.addVariableToContext("brandId", Long.toString(b.getId()));
                scraper.execute();
                log.info("successful processing brand " + b.getName());
            } catch (Exception exception) {
                log.error("cannot process Ria. brand name = " + b.getName(), exception);
            }
        }
        log.info("Ria: succecsful");
        //result.add(scraper.getContext().getVar("temp").toString());
    }
}
