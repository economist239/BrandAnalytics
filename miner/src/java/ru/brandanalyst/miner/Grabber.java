package ru.brandanalyst.miner;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import java.util.List;

/**
 *
 * @author Александр
 */
public abstract class Grabber {
    protected SimpleJdbcTemplate jdbcTemplate;
    protected String config;
    public abstract void grab();
    public abstract void setConfig(String config);
    public abstract void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate);
}

