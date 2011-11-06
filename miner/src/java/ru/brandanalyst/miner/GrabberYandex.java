package ru.brandanalyst.miner;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.webharvest.runtime.Scraper;
import org.webharvest.definition.ScraperConfiguration;
import ru.brandanalyst.miner.listener.YandexNewsScraperRuntimeListener;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * @author Александр Сенов
 */
public class GrabberYandex extends Grabber {

    private static final Logger log = Logger.getLogger(GrabberYandex.class);

    public void setConfig(String config) {
        this.config = config;  //not using    //config = urls + ";" + webharvestconfig // now its = "config/config1.xml"
    }

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void grab(Date timeLimit) {
        try {
            ScraperConfiguration config = new ScraperConfiguration(this.config);
            Scraper scraper = new Scraper(config, ".");
            scraper.setDebug(true);
            scraper.addRuntimeListener(new YandexNewsScraperRuntimeListener(this.jdbcTemplate, timeLimit));
            scraper.execute();
            //result.add(scraper.getContext().getVar("temp").toString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
