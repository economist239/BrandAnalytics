package ru.brandanalyst.analyzer.analyzer.quant;

import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.model.Article;

/**
 * Created by IntelliJ IDEA.
 * User: daddy-bear
 * Date: 2/25/12
 * Time: 11:29 AM
 */
public abstract class StubAnalyzer implements AbstractAnalyzer {
    @Override
    public void onStart() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void init(ProvidersHandler pureProvidersHandler) {
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
