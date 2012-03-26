package ru.brandanalyst.analyzer.analyzer.quant;

import java.util.Random;
import ru.brandanalyst.core.model.Article;

/**
 * Alexandra Mikhaylova mikhaylova@yandex-team.ru
 */
public class RandomAnalyzer extends AbstractSentimentAnalyzer {
    private Random randomGenerator;

    @Override
    public void onStart() {
        super.onStart();
        this.randomGenerator = new Random();
    }

    @Override
    public void analyze(Article article) {
        final double clsRandom = this.randomGenerator.nextInt(3) - 1;
        if (clsRandom == -1.0) {
            addPointToGraph(article, article.getBrandId(), this.graphDepotNegative);
        }
        if (clsRandom == 0.0) {
            addPointToGraph(article, article.getBrandId(), this.graphDepotNeutral);
        }
        if (clsRandom == 1.0) {
            addPointToGraph(article, article.getBrandId(), this.graphDepotPositive);
        }
    }
}
