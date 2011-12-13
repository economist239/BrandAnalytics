package ru.brandanalyst.miner;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.ProvidersHandler;

import java.util.Date;

/**
 * @author Александр
 *         main single grabber class
 */
public abstract class Grabber {
    protected ProvidersHandler dirtyProvidersHandler;
    protected String config;

    public abstract void grab(Date timeLimit);

    public void setDirtyProvidersHandler(ProvidersHandler dirtyProvidersHandler) {
        this.dirtyProvidersHandler = dirtyProvidersHandler;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}

