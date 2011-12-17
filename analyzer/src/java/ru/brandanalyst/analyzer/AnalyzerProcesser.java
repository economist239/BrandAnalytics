package ru.brandanalyst.analyzer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import ru.brandanalyst.analyzer.analyzers.AbstractAnalyzer;

import java.util.List;

/**
 * Класс, в котором выполняется весь анализ
 * Created by IntelliJ IDEA.
 * User: Nikolaj Karpov
 * Date: 23.10.11
 * Time: 21:10
 *
 * @version 1.0
 */
public class AnalyzerProcesser implements InitializingBean {
    private static final Logger log = Logger.getLogger(AnalyzerProcesser.class);
    private List<AbstractAnalyzer> analyzers;

    @Required
    public void setAnalyzers(List<AbstractAnalyzer> analyzers) {
        this.analyzers = analyzers;
    }

    public final void afterPropertiesSet() {
        log.info("analyzing started...");

        for (AbstractAnalyzer a : analyzers) {
            a.analyze();
        }

        log.info("analyzing finished succesful");
    }

}
