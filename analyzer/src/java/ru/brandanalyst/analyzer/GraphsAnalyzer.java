package ru.brandanalyst.analyzer;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.*;
import ru.brandanalyst.core.model.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import ru.brandanalyst.core.time.TimeProperties;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/26/11
 * Time: 3:14 PM
 */
public class GraphsAnalyzer {
    private static final Logger log = Logger.getLogger(GraphsAnalyzer.class);

    private SimpleJdbcTemplate dirtyJdbcTemplate;
    private SimpleJdbcTemplate pureJdbcTemplate;

   // public final static long TIME_LIMIT = (long) (13174128) * (long) (100000);
   // private final static long TIME_STEP = 86400000;

    public GraphsAnalyzer(SimpleJdbcTemplate pureJdbcTemplate, SimpleJdbcTemplate dirtyJdbcTemplate) {
        this.dirtyJdbcTemplate = dirtyJdbcTemplate;
        this.pureJdbcTemplate = pureJdbcTemplate;
    }

    public void analyze() {

        log.info("graph analyzing started...");
        BrandProvider dirtyBrandProvider = new BrandProvider(dirtyJdbcTemplate);
        BrandProvider pureBrandProvider = new BrandProvider(pureJdbcTemplate);
        ArticleProvider dirtyArticleProvider = new ArticleProvider(dirtyJdbcTemplate);
        ArticleProvider pureArticleProvider = new ArticleProvider(pureJdbcTemplate);
        GraphProvider pureGraphProvider = new GraphProvider(pureJdbcTemplate);

        Map<Long, Double> graphMap = new HashMap<Long, Double>(); //out of memory

        //counting value
        for (Brand b : dirtyBrandProvider.getAllBrands()) {
            pureBrandProvider.writeBrandToDataStore(b);

            for (long t = TimeProperties.TIME_LIMIT; t < new Date().getTime(); t += TimeProperties.SINGLE_DAY) {
                graphMap.put(t, 0.0);
            }

            for (Article a : dirtyArticleProvider.getAllArticlesByBrand(b.getId())) {
                Timestamp timestamp = a.getTstamp();
                try{
                    graphMap.put(timestamp.getTime(), graphMap.get(timestamp.getTime()) + 1.0);
                }catch(Exception e) {
                    log.error("can't process dot");
                }
                pureArticleProvider.writeArticleToDataStore(a);
            }
            try {
                //map to graph
                Graph graph = new Graph("");
                for (long t = TimeProperties.TIME_LIMIT; t < new Date().getTime(); t += TimeProperties.SINGLE_DAY) {
                    graph.addPoint(new SingleDot(new Timestamp(t), graphMap.get(t)));

                }
                pureGraphProvider.writeGraph(graph, b.getId(), 1);
            } catch (NullPointerException e) {
                log.error("cannot create graph for brand: " + b.getId());
            }
        }
        log.info("graph analazing finished succesful");
    }
}
