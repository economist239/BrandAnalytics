package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.Yalet;
import ru.brandanalyst.core.db.provider.ProvidersHandler;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/16/11
 * Time: 3:59 PM
 */
public abstract class AbstractDbYalet implements Yalet {
    protected ProvidersHandler providersHandler;

    public void setProvidersHandler(ProvidersHandler providersHandler) {
        this.providersHandler = providersHandler;
    }
}
