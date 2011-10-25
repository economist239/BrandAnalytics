package ru.brandanalyst.miner;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import ru.brandanalyst.core.db.provider.BrandProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.miner.listener.RiaNewsScraperRuntimeListener;

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
    public void setConfig(String config) {
        this.config = config;
    }

    @Override
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void grab() {
        try {
            for (Brand b : new BrandProvider(jdbcTemplate).getAllBrands()) {
                ScraperConfiguration config = new ScraperConfiguration(this.config);
                Scraper scraper = new Scraper(config, ".");
                scraper.setDebug(true);
                scraper.addRuntimeListener(new RiaNewsScraperRuntimeListener(this.jdbcTemplate));
                scraper.addVariableToContext("riaQueryURL", searchURL + b.getName() + "&p="); //"$p" - suffix for result page number
                scraper.addVariableToContext("riaAbsoluteURL", sourceURL);
                scraper.addVariableToContext("brandId", Long.toString(b.getId()));
                scraper.execute();
            }
            log.error("Ria: succecsful");
            //result.add(scraper.getContext().getVar("temp").toString());
        } catch (Exception exception) {
            log.error("cannot process Ria");
        }
    }
}
