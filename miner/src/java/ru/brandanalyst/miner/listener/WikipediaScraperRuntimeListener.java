package ru.brandanalyst.miner.listener;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperRuntimeListener;
import org.webharvest.runtime.processors.BaseProcessor;
import org.webharvest.runtime.variables.Variable;
import ru.brandanalyst.core.db.provider.BrandProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.miner.util.DataTransformator;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/26/11
 * Time: 12:29 PM
 */
public class WikipediaScraperRuntimeListener implements ScraperRuntimeListener {
        //private int i = 0;
    private static final Logger log = Logger.getLogger(WikipediaScraperRuntimeListener.class);

    protected SimpleJdbcTemplate jdbcTemplate;
    protected BrandProvider brandProvider;

    public WikipediaScraperRuntimeListener(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        brandProvider = new BrandProvider(jdbcTemplate);
    }

    public void onExecutionStart(Scraper scraper) {
    }

    public void onExecutionPaused(Scraper scraper) {
    }

    public void onExecutionContinued(Scraper scraper) {
    }

    public void onNewProcessorExecution(Scraper scraper, BaseProcessor baseProcessor) {

    }

    public void onExecutionEnd(Scraper scraper) {

    }

    private String getWebSite(String brandWebsite1, String brandWebsite2) {
        String brandWebsite;

        if (brandWebsite1.indexOf('.') >= 0) {
            brandWebsite = brandWebsite1;
        } else {
            brandWebsite = brandWebsite2;
        }
        int whiteSpace = brandWebsite.indexOf('\n');
        if (whiteSpace > 0) {
            brandWebsite = brandWebsite.substring(0,whiteSpace);
        }

        return brandWebsite;
    }

    private String getDescription(String brandDescription1, String brandDescription2) {
        brandDescription1 = DataTransformator.clearString(brandDescription1);
        brandDescription2 = DataTransformator.clearString(brandDescription2);

        String brandDescription;
        if(brandDescription1.indexOf('—') >= 0) {
            brandDescription = brandDescription1;
        } else {
            brandDescription = brandDescription2;
        }


        int a, b;
        while (brandDescription.indexOf('[') != (-1)) {
            a = brandDescription.indexOf('[');
            b = brandDescription.indexOf(']');
            if (b > a) {
                brandDescription = brandDescription.substring(0, a) + brandDescription.substring(b + 1, brandDescription.length());
            }
        }
        return brandDescription;
    }

    public void onProcessorExecutionFinished(Scraper scraper, BaseProcessor baseProcessor, Map map) {
        if ("body".equalsIgnoreCase(baseProcessor.getElementDef().getShortElementName())) {
            String brandWebsite1 = ((Variable) scraper.getContext().get("brandWebsite1")).toString();
            String brandWebsite2 = ((Variable) scraper.getContext().get("brandWebsite2")).toString();
            String brandWebsite = getWebSite(brandWebsite1, brandWebsite2);

            String brandName = ((Variable) scraper.getContext().get("brandName")).toString();

            String brandDescription1 = ((Variable) scraper.getContext().get("brandDescription1")).toString();
            String brandDescription2 = ((Variable) scraper.getContext().get("brandDescription2")).toString();
            String brandDescription = getDescription(brandDescription1, brandDescription2);

            Brand brand = new Brand(-1, brandName, brandDescription, brandWebsite, -1);

            brandProvider.writeBrandToDataStore(brand);
            log.info("Wikipedia: brand added...");
        }
    }

    public void onExecutionError(Scraper scraper, Exception e) {
        if (e != null) {
            log.error("WikipediaNewsScraperRuntimeListener error");
        }
    }
}
