package ru.brandanalyst.miner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import ru.brandanalyst.common.scheduler.AbstractDelayedTimerTask;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/9/12
 */
@Deprecated
public class GrabberHandler extends AbstractDelayedTimerTask {
    private static final Logger log = Logger.getLogger(GrabberHandler.class);

    private List<AbstractGrabberTask> grabberTaskList;

    @Required
    public void setGrabberTaskList(List<AbstractGrabberTask> grabberTaskList) {
        this.grabberTaskList = grabberTaskList;
    }

    @Override
    public void runTask() {
        log.info("miner started...");
        for (AbstractGrabberTask g : grabberTaskList) {
            g.grab();
        }
    }
}
