package ru.brandanalyst.analyzer.analyzer.quant;

import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.model.Article;

/**
 * Alexandra Mikhaylova mikhaylova@yandex-team.ru
 */
public class RandomAnalyzer implements AbstractAnalyzer {
    private GraphProvider graphProvider;

    @Override
    public void init(ProvidersHandler pureProvidersHandler) {
        this.graphProvider = pureProvidersHandler.getGraphProvider();
    }

    @Override
    public void onStart() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void analyze(Article article) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void flush() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
