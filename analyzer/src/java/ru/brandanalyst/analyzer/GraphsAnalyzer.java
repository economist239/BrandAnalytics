package ru.brandanalyst.analyzer;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/26/11
 * Time: 3:14 PM
 */
public class GraphsAnalyzer {
    private SimpleJdbcTemplate dirtyJdbcTemplate;
    private SimpleJdbcTemplate pureJdbcTemplate;

    private final int TIME_PERIOD = 1;

    public GraphsAnalyzer(SimpleJdbcTemplate pureJdbcTemplate, SimpleJdbcTemplate dirtyJdbcTemplate) {
        this.dirtyJdbcTemplate = dirtyJdbcTemplate;
        this.pureJdbcTemplate = pureJdbcTemplate;
    }

    public void analyze() {

    }
}
