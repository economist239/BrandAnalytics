package ru.brandanalyst.common.scheduler;

import org.springframework.scheduling.timer.ScheduledTimerTask;

import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/9/12
 * Time: 10:14 PM
 *
 * requires to set only period
 */
public abstract class AbstractDelayedTimerTask extends ScheduledTimerTask {
    private static final long DEFAULT_DELAY = 0;

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
