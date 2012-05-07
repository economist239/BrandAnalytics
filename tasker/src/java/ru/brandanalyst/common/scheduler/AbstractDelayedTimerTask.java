package ru.brandanalyst.common.scheduler;

import org.springframework.scheduling.timer.ScheduledTimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/9/12
 * Time: 10:14 PM
 * <p/>
 * requires to set only period
 */
public abstract class AbstractDelayedTimerTask extends ScheduledTimerTask {
    private static final long DEFAULT_DELAY = 10000;

    protected AbstractDelayedTimerTask() {
        super();
        setDelay(DEFAULT_DELAY);
        setRunnable(new Runnable() {
            @Override
            public void run() {
                runTask();
            }
        });

    }

    protected abstract void runTask();
}
