package ru.brandanalyst.monitoring;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.common.scheduler.AbstractDelayedTimerTask;

import java.util.List;

/**
 * User: daddy-bear
 * Date: 30.04.12
 * Time: 12:23
 */
public class MailingMonitor extends AbstractDelayedTimerTask {

    private SimpleJdbcTemplate jdbcTemplate;

    private List<RemoteTopSnapshot> remoteTopSnapshots;

    @Required
    public void setRemoteTopSnapshots(List<RemoteTopSnapshot> remoteTopSnapshots) {
        this.remoteTopSnapshots = remoteTopSnapshots;
    }

    @Required
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    protected void runTask() {
        final StringBuilder sb = new StringBuilder();

        for (RemoteTopSnapshot s : remoteTopSnapshots) {
            sb.append(s.buildSnapshot()).append("\n\n---\n\n");
        }

        final Long articleCount = jdbcTemplate.queryForLong("select count(*) from Article");
    }
}
