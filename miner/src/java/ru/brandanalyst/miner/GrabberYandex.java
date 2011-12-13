package ru.brandanalyst.miner;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import ru.brandanalyst.miner.listener.YandexNewsScraperRuntimeListener;

import java.util.Date;

/**
 * @author Александр Сенов
 */
@Deprecated
public class GrabberYandex extends Grabber {
    private static final Logger log = Logger.getLogger(GrabberYandex.class);

    public void grab(Date timeLimit) {
        try {
            ScraperConfiguration config = new ScraperConfiguration(this.config);
            Scraper scraper = new Scraper(config, ".");
            scraper.setDebug(true);
            scraper.addRuntimeListener(new YandexNewsScraperRuntimeListener(dirtyProvidersHandler, timeLimit));
            scraper.execute();
            //result.add(scraper.getContext().getVar("temp").toString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
