package ru.brandanalyst.analyzer.analyzer.quant;

import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.Graph;

/**
 * @author daddy-bear
 *         Date: 03.03.12
 */
public class SvmAnalyzer implements AbstractAnalyzer {

    @Override
    public void init(ProvidersHandler pureProvidersHandler) {

    }

    @Override
    public void onStart() {
       
    }

    @Override
    public void analyze(Article article) {
    // TODO: article to Instance object, create method in ClassifierUtils.java
    }

    @Override
    public void flush() {

    }
}
