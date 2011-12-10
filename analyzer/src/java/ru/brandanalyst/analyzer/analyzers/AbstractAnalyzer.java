package ru.brandanalyst.analyzer.analyzers;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/10/11
 * Time: 10:30 PM
 */
public abstract class AbstractAnalyzer {
    protected static final Logger log = Logger.getLogger(AbstractAnalyzer.class);

    protected SimpleJdbcTemplate dirtyJdbcTemplate;
    protected SimpleJdbcTemplate pureJdbcTemplate;

    public void setDirtyJdbcTemplate(SimpleJdbcTemplate dirtyJdbcTemplate) {
        this.dirtyJdbcTemplate = dirtyJdbcTemplate;
    }

    public void setPureJdbcTemplate(SimpleJdbcTemplate pureJdbcTemplate) {
        this.pureJdbcTemplate = pureJdbcTemplate;
    }

    public abstract void analyze();
}
