package ru.brandanalyst.analyzer.analyzer.quant;

import org.springframework.beans.factory.annotation.Required;
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
    private String classifierNegativeFileName;
    private String classifierPositiveFileName;

    @Required
    public void setClassifierNegativeFileName(final String fileName) {
        this.classifierNegativeFileName = fileName;
    }

    @Required
    public void setClassifierPositiveFileName(final String fileName) {
        this.classifierPositiveFileName = fileName;
    }

    @Override
    public void init(ProvidersHandler pureProvidersHandler) {
        super.init(pureProvidersHandler);
        classifierNegative = ClassifierUtils.loadSVMClassifier(this.classifierNegativeFileName);
        classifierPositive = ClassifierUtils.loadSVMClassifier(this.classifierPositiveFileName);
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
