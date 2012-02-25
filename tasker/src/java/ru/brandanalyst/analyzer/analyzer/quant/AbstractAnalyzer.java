package ru.brandanalyst.analyzer.analyzer.quant;

import org.apache.log4j.Logger;
import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.model.Article;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/10/11
 * Time: 10:30 PM
 */
public interface AbstractAnalyzer {
    void init(ProvidersHandler pureProvedrsHandler);

    void analyze(Article article);

    void flush();
}
