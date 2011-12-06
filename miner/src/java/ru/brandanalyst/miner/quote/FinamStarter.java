package ru.brandanalyst.miner.quote;


import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author OlegPan
 * This class starts grab from finam
 */

public class FinamStarter {
    protected SimpleJdbcTemplate jdbcTemplate;
    private static final Logger log = Logger.getLogger(FinamStarter.class);
    private final int beginDay=23;
    private final int beginMonth=11;
    private final int beginYear=2009;

        public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        public void afterPropertiesSet() {
           new GrabberFinam(jdbcTemplate).grab(beginDay,beginMonth,beginYear);
        }

}
