package ru.brandanalyst.miner.grabbers;

import ru.brandanalyst.core.db.provider.ProvidersHandler;

import java.util.Date;

/**
 * @author Александр
 *         main single grabber class
 */
public abstract class Grabber {
    protected ProvidersHandler handler;
    protected String config;

    public abstract void grab(Date timeLimit);

    public void setDirtyProvidersHandler(ProvidersHandler handler) {
        this.handler = handler;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}

