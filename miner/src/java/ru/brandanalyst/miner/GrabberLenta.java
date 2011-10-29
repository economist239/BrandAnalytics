package ru.brandanalyst.miner;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import ru.brandanalyst.core.db.provider.BrandProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.miner.listener.LentaScraperRuntimeListener;

public class GrabberLenta extends Grabber{
    private static final Logger log = Logger.getLogger(GrabberLenta.class);

    private static final String searchURL = "http://lenta.ru/search/?query=";
    private static final String sourceURL = "http://lenta.ru";

    @Override
    public void setConfig(String config) {
        this.config=config;
    }

    @Override
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void grab() {
        try {
            for(Brand b : new BrandProvider(jdbcTemplate).getAllBrands()){
                ScraperConfiguration config = new ScraperConfiguration(this.config);
                Scraper scraper = new Scraper(config, ".");
                scraper.setDebug(true);
                scraper.addRuntimeListener(new LentaScraperRuntimeListener(this.jdbcTemplate));
                scraper.addVariableToContext("lentaQueryURL",searchURL + b.getName() + "&page=");//"$page" - suffix for result page number
                scraper.addVariableToContext("lentaAbsoluteURL", sourceURL);
                scraper.addVariableToContext("brandId", Long.toString(b.getId()));
                scraper.execute();
            }
            log.info("Lenta: successful");
            //result.add(scraper.getContext().getVar("temp").toString());
        } catch (Exception exception) {
            log.error("cannot process Lenta");
        }
    }
}
