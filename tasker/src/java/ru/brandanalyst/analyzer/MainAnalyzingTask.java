package ru.brandanalyst.analyzer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import ru.brandanalyst.analyzer.analyzer.quant.AbstractAnalyzer;
import ru.brandanalyst.common.scheduler.AbstractDelayedTimerTask;

import java.util.List;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/9/12
 * Time: 3:27 PM
 */
public class MainAnalyzingTask extends AbstractDelayedTimerTask {
    private static final Logger log = Logger.getLogger(MainAnalyzingTask.class);
    private List<AbstractAnalyzer> analyzers;

    @Required
    public void setAnalyzers(List<AbstractAnalyzer> analyzers) {
        this.analyzers = analyzers;
    }

    @Override
    public final void runTask() {
        log.info("analyzing started...");

        for (AbstractAnalyzer a : analyzers) {
            a.analyze();
        }

        log.info("analyzing finished succesful");
    }
}
