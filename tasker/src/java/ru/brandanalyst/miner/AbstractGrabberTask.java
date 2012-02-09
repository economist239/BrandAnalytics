package ru.brandanalyst.miner;

import org.springframework.beans.factory.annotation.Required;
import ru.brandanalyst.common.scheduler.AbstractDelayedTimerTask;
import ru.brandanalyst.core.db.provider.ProvidersHandler;

import java.util.Date;

/**
 * @author Александр
 *         main single grabber class
 */
public abstract class AbstractGrabberTask extends AbstractDelayedTimerTask {
    protected ProvidersHandler handler;
    protected String config;

    protected abstract void grab();

    @Required
    public void setDirtyProvidersHandler(ProvidersHandler handler) {
        this.handler = handler;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    @Override
    public void runTask() {
        grab();
    }
}

