package ru.brandanalyst.analyzer.analyzers;

import org.apache.log4j.Logger;
import ru.brandanalyst.core.db.provider.ProvidersHandler;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/10/11
 * Time: 10:30 PM
 */
public abstract class AbstractAnalyzer {
    protected static final Logger log = Logger.getLogger(AbstractAnalyzer.class);

    protected ProvidersHandler dirtyProvidersHandler;
    protected ProvidersHandler pureProvidersHandler;

    public void setDirtyProvidersHandler(ProvidersHandler dirtyProvidersHandler) {
        this.dirtyProvidersHandler = dirtyProvidersHandler;
    }

    public void setPureProvidersHandler(ProvidersHandler pureProvidersHandler) {
        this.pureProvidersHandler = pureProvidersHandler;
    }

    public abstract void analyze();
}
