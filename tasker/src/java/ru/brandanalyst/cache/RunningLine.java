package ru.brandanalyst.cache;

import org.springframework.beans.factory.annotation.Required;
import ru.brandanalyst.common.scheduler.AbstractDelayedTimerTask;
import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;

/**
 * User: dima
 * Date: 2/26/12
 * Time: 1:54 PM
 */
public class RunningLine extends AbstractDelayedTimerTask {
    private GraphProvider graphProvider;
    private String runningLineFile;

    @Required
    public void setRunningLineFile(String runningLineFile) {
        this.runningLineFile = runningLineFile;
    }

    @Required
    public void setPureProvidersHandler(ProvidersHandler providersHandler) {
        graphProvider = providersHandler.getGraphProvider();    
    }
    
    @Override
    protected void runTask() {
        ///TODO
    }
}
