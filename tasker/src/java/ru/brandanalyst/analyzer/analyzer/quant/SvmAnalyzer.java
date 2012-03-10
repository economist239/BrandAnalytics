package ru.brandanalyst.analyzer.analyzer.quant;

import ru.brandanalyst.analyzer.classifiers.SVMClassifier;
import ru.brandanalyst.analyzer.util.ClassifierUtils;
import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.model.Article;
import weka.core.Instance;

/**
 * @author daddy-bear
 *         Date: 03.03.12
 */
public class SvmAnalyzer extends AbstractSentimentAnalyzer {
    private SVMClassifier classifierNegative;
    private SVMClassifier classifierPositive;

    @Override
    public void init(ProvidersHandler pureProvidersHandler) {
        super.init(pureProvidersHandler);
        classifierNegative = ClassifierUtils.loadSVMClassifier("svmclassifiers/svmNegative");  // TODO: build !
        classifierPositive = ClassifierUtils.loadSVMClassifier("svmclassifiers/svmPositive");
    }

    @Override
    public void analyze(Article article) {
        final long brandId = article.getBrandId();
        final String tweet = article.getContent();
        final Instance instance = ClassifierUtils.createInstance(tweet);

        // Attention! yes = 0.0, no = 1.0
        double positive = this.classifierPositive.classifyInstance(instance);
        if (positive == 0.0) {
            addPointToGraph(article, brandId, this.graphDepotPositive);
        } else {
            addPointToGraph(article, brandId, this.graphDepotNeutral);
        }
        double negative = this.classifierNegative.classifyInstance(instance);
        if (negative == 0.0) {
            addPointToGraph(article, brandId, this.graphDepotNegative);
        } else {
            addPointToGraph(article, brandId, this.graphDepotNeutral);
        }
    }
}
