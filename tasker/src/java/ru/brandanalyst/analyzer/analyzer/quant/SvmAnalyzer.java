package ru.brandanalyst.analyzer.analyzer.quant;

import ru.brandanalyst.analyzer.classifiers.SVMClassifier;
import ru.brandanalyst.analyzer.util.ClassifierUtils;
import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.Graph;
import weka.core.Instance;

import java.util.HashMap;
import java.util.Map;

/**
 * @author daddy-bear
 *         Date: 03.03.12
 */
public class SvmAnalyzer implements AbstractAnalyzer {
    // according to table Ticker in ba_dirty and ba_pure
    private static final int NEGATIVE_SVM_TICKER_ID = 4;
    private static final int NEUTRAL_SVM_TICKER_ID = 3;  // TODO: add graph for neutral references !
    private static final int POSITIVE_SVM_TICKER_ID = 2;

    private SVMClassifier classifierNegative;
    private SVMClassifier classifierPositive;

    private GraphProvider graphProvider;

    private Map<Long, Graph> graphDepotPositive;
    private Map<Long, Graph> graphDepotNegative;

    @Override
    public void init(ProvidersHandler pureProvidersHandler) {
        graphProvider = pureProvidersHandler.getGraphProvider();
        classifierNegative = ClassifierUtils.loadSVMClassifier("svmclassifiers/svmNegative");
        classifierPositive = ClassifierUtils.loadSVMClassifier("svmclassifiers/svmPositive");
    }

    @Override
    public void onStart() {
        graphDepotNegative = new HashMap<Long, Graph>();
        graphDepotPositive = new HashMap<Long, Graph>();
    }

    @Override
    public void analyze(Article article) {
        final long brandId = article.getBrandId();
        final String tweet = article.getContent();
        final Instance instance = ClassifierUtils.createInstance(tweet);

        // Attention! yes = 0.0, no = 1.0
        double positive = classifierPositive.classifyInstance(instance);
        if (positive == 0.0) {
            if (graphDepotPositive.containsKey(brandId)) {
                Graph graph = graphDepotPositive.get(brandId);
                graph.addPoint(article.getTstamp().toLocalDate(), 1);
            } else {
                Graph graph = new Graph();
                graph.addPoint(article.getTstamp().toLocalDate(), 1);
                graphDepotPositive.put(brandId, graph);
            }
        }
        double negative = classifierNegative.classifyInstance(instance);
        if (negative == 0.0) {
            if (graphDepotNegative.containsKey(brandId)) {
                Graph graph = graphDepotNegative.get(brandId);
                graph.addPoint(article.getTstamp().toLocalDate(), 1);
            } else {
                Graph graph = new Graph();
                graph.addPoint(article.getTstamp().toLocalDate(), 1);
                graphDepotNegative.put(brandId, graph);
            }
        }
    }

    @Override
    public void flush() {
        for (Map.Entry<Long, Graph> e: graphDepotNegative.entrySet()) {
            graphProvider.writeGraph(e.getValue(), e.getKey(), NEGATIVE_SVM_TICKER_ID);
        }
        graphDepotNegative.clear();

        for (Map.Entry<Long, Graph> e: graphDepotPositive.entrySet()) {
            graphProvider.writeGraph(e.getValue(), e.getKey(), POSITIVE_SVM_TICKER_ID);
        }
        graphDepotPositive.clear();
    }
}
