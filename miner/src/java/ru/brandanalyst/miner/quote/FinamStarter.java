package ru.brandanalyst.miner.quote;


import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * @author OlegPan
 * This class starts grab from finam
 */

public class FinamStarter {
    protected SimpleJdbcTemplate jdbcTemplate;
    private static final Logger log = Logger.getLogger(FinamStarter.class);
    private final int beginDay=15;
    private final int beginMonth=10;

        public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        public void afterPropertiesSet() {
            new GrabberFinam(jdbcTemplate).grab(beginDay,beginMonth);
        }

}
