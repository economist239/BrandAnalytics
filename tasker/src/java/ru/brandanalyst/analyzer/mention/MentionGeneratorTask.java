package ru.brandanalyst.analyzer.mention;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.common.scheduler.AbstractDelayedTimerTask;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.util.Batch;

import java.util.List;
import java.util.Map;

/**
 * @author daddy-bear
 *         Date: 03.05.12 - 19:22
 */

public class MentionGeneratorTask extends AbstractDelayedTimerTask {

    private final static long BASE_TICKER = 1L;

    private GraphProvider graphProvider;

    @Required
    public void setGraphProvider(GraphProvider graphProvider) {
        this.graphProvider = graphProvider;
    }

    @Override
    protected void runTask() {
        final Map<Long, Graph> graphsByBrandId = graphProvider.getGraphsByTickerId(BASE_TICKER);

        for (final Map.Entry<Long, Graph> e: graphsByBrandId.entrySet()) {
            final Graph graph = e.getValue();
            final long  brandId = e.getKey();

            //TODO build mention
        }
    }
}
