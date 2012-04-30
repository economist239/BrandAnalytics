package ru.brandanalyst.monitoring;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.common.scheduler.AbstractDelayedTimerTask;

/**
 * User: daddy-bear
 * Date: 30.04.12
 * Time: 12:23
 */
public class MailingMonitor extends AbstractDelayedTimerTask {

    private SimpleJdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    protected void runTask() {
        Long articleCount = jdbcTemplate.queryForLong("select count(*) from Article");


    }
}
