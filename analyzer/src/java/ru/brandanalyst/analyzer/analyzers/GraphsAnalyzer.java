package ru.brandanalyst.analyzer.analyzers;

import ru.brandanalyst.core.db.provider.interfaces.ArticleProvider;
import ru.brandanalyst.core.db.provider.interfaces.BrandProvider;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.model.SingleDot;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, строящий основные временные ряды по данным из новостных источников
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/26/11
 * Time: 3:14 PM
 *
 * @version 1.0
 */
public class GraphsAnalyzer extends AbstractAnalyzer {

    public void analyze() {

        log.info("graph analyzing started...");
        BrandProvider dirtyBrandProvider = dirtyProvidersHandler.getBrandProvider();
        BrandProvider pureBrandProvider = pureProvidersHandler.getBrandProvider();
        ArticleProvider dirtyArticleProvider = dirtyProvidersHandler.getArticleProvider();
        ArticleProvider pureArticleProvider = pureProvidersHandler.getArticleProvider();
        GraphProvider pureGraphProvider = pureProvidersHandler.getGraphProvider();

        Map<Long, Double> graphMap = new HashMap<Long, Double>(); //out of memory

        //counting value
        for (Brand b : dirtyBrandProvider.getAllBrands()) {
            pureBrandProvider.writeBrandToDataStore(b); //it shouldn't be here

            for (Article a : dirtyArticleProvider.getAllOfficialArticlesByBrand(b.getId())) {

                Timestamp timestamp = a.getTstamp();
                if (graphMap.containsKey(timestamp.getTime())) {
                    graphMap.put(timestamp.getTime(), graphMap.get(timestamp.getTime()) + 1.0);
                } else {
                    graphMap.put(timestamp.getTime(), 1.0);
                }

                pureArticleProvider.writeArticleToDataStore(a);
            }
            //map to graph
            Graph graph = new Graph("");
            for (Long time : graphMap.keySet()) {
                graph.addPoint(new SingleDot(new Timestamp(time), graphMap.get(time)));
            }

            pureGraphProvider.writeGraph(graph, b.getId(), 1);
        }
        log.info("graph analazing finished succesful");
    }
}
