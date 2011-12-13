package ru.brandanalyst.frontend.services;

import ru.brandanalyst.core.db.provider.ProvidersHandler;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/13/11
 * Time: 10:25 PM
 */
public class AbstractManager {
    protected final ProvidersHandler providersHandler;

    public AbstractManager(ProvidersHandler providersHandler) {
        this.providersHandler = providersHandler;
    }
}
