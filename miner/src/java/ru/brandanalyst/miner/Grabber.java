package ru.brandanalyst.miner;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * @author Александр
 * main single grabber class
 */
public abstract class Grabber {
    protected SimpleJdbcTemplate jdbcTemplate;
    protected String config;

    public abstract void grab();

    public abstract void setConfig(String config);

    public abstract void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate);
}

