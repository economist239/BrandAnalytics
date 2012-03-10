package ru.brandanalyst.analyzer.analyzer.quant;

import org.joda.time.LocalDate;
import ru.brandanalyst.core.db.provider.ProvidersHandler;
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
public class GraphsAnalyzer implements AbstractAnalyzer {

    public static final int REFERENCES_TICKER_ID = 1;
    private GraphProvider graphProvider;

    private Map<Long, Graph> graphDepot;

    @Override
    public void init(ProvidersHandler pureProvedrsHandler) {
        graphProvider = pureProvedrsHandler.getGraphProvider();
    }

    @Override
    public void onStart() {
        graphDepot = new HashMap<Long, Graph>();
    }

    @Override
    public void analyze(Article article) {
        long brandId = article.getBrandId();
        if (graphDepot.containsKey(brandId)) {
            Graph graph = graphDepot.get(brandId);
            graph.addPoint(article.getTstamp().toLocalDate(), 1);
        } else {
            Graph graph = new Graph();
            graph.addPoint(article.getTstamp().toLocalDate(), 1);
            graphDepot.put(brandId, graph);
        }
    }

    @Override
    public void flush() {
        for (Map.Entry<Long, Graph> e: graphDepot.entrySet()) {
            graphProvider.writeGraph(e.getValue(), e.getKey(), REFERENCES_TICKER_ID);
        }
        graphDepot.clear();
    }
}
