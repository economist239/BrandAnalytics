package ru.brandanalyst.miner;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import ru.brandanalyst.core.db.provider.BrandProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.miner.listener.FontankaScraperRuntimeListener;
import ru.brandanalyst.miner.util.DataTransformator;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/26/11
 * Time: 4:13 AM
 */
public class GrabberFontanka extends Grabber {

    private static final Logger log = Logger.getLogger(GrabberFontanka.class);

    private static final String beginSearchURL = "http://www.fontanka.ru/cgi-bin/search.scgi?query=";
    private static final String endSearchURL = "&fyear=2010&fmon=01&fday=01&tyear=2011&tmon=10&tday=30&rubric=fontanka&sortt=date&offset=";
    private static final String sourceURL = "http://fontanka.ru";

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

        for (Brand b : new BrandProvider(jdbcTemplate).getAllBrands()) {
            try {

                ScraperConfiguration config = new ScraperConfiguration(this.config);
                Scraper scraper = new Scraper(config, ".");
                scraper.addRuntimeListener(new FontankaScraperRuntimeListener(jdbcTemplate));
                String query = DataTransformator.stringToHexQueryString(b.getName());

                System.out.println(b.getName());
                scraper.addVariableToContext("QueryURL", beginSearchURL + query + endSearchURL); //"$p" - suffix for result page number
                scraper.addVariableToContext("AbsoluteURL", sourceURL);
                scraper.addVariableToContext("brandId", Long.toString(b.getId()));
                scraper.execute();
                log.info("successful processing brand " + b.getName());
            } catch (Exception exception) {
                exception.printStackTrace();
                log.error("cannot process Fontanka brand " + b.getName());
            }
        }
        log.info("Fontanka: succecsful");
    }
}
