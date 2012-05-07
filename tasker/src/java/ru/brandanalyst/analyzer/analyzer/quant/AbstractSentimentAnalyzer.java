package ru.brandanalyst.analyzer.analyzer.quant;

import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.Graph;

import java.util.HashMap;
import java.util.Map;

/**
 * Alexandra Mikhaylova mikhaylova@yandex-team.ru
 */
public abstract class AbstractSentimentAnalyzer implements AbstractAnalyzer {
    // according to table Ticker in ba_dirty and ba_pure
    private static final int POSITIVE_SVM_TICKER_ID = 2;
    private static final int NEUTRAL_SVM_TICKER_ID = 3;
    private static final int NEGATIVE_SVM_TICKER_ID = 4;

    protected GraphProvider graphProvider;

    protected Map<Long, Graph> graphDepotPositive;
    protected Map<Long, Graph> graphDepotNeutral;
    protected Map<Long, Graph> graphDepotNegative;

    @Override
    public void init(ProvidersHandler pureProvidersHandler) {
        this.graphProvider = pureProvidersHandler.getGraphProvider();
    }

    @Override
    public void onStart() {
        this.graphDepotPositive = new HashMap<Long, Graph>();
        this.graphDepotNeutral = new HashMap<Long, Graph>();
        this.graphDepotNegative = new HashMap<Long, Graph>();
    }

    @Override
    public void flush() {
        for (Map.Entry<Long, Graph> e : this.graphDepotPositive.entrySet()) {
            this.graphProvider.writeGraph(e.getValue(), e.getKey(), POSITIVE_SVM_TICKER_ID);
        }
        this.graphDepotPositive.clear();

        for (Map.Entry<Long, Graph> e : this.graphDepotNeutral.entrySet()) {
            this.graphProvider.writeGraph(e.getValue(), e.getKey(), NEUTRAL_SVM_TICKER_ID);
        }
        this.graphDepotNeutral.clear();

        for (Map.Entry<Long, Graph> e : this.graphDepotNegative.entrySet()) {
            this.graphProvider.writeGraph(e.getValue(), e.getKey(), NEGATIVE_SVM_TICKER_ID);
        }
        this.graphDepotNegative.clear();
    }

    protected void addPointToGraph(final Article article, final long brandId, final Map<Long, Graph> graphDepot) {
        if (graphDepot.containsKey(brandId)) {
            Graph graph = graphDepot.get(brandId);
            graph.addPoint(article.getTstamp().toLocalDate(), 1);
        } else {
            Graph graph = new Graph();
            graph.addPoint(article.getTstamp().toLocalDate(), 1);
            graphDepot.put(brandId, graph);
        }
    }
}
