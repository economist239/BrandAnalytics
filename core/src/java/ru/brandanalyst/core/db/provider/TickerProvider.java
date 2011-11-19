package ru.brandanalyst.core.db.provider;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/19/11
 * Time: 2:46 PM
 */
public class TickerProvider {
    private static final Logger log = Logger.getLogger(GraphProvider.class);

    private SimpleJdbcTemplate jdbcTemplate; //

    public TickerProvider(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public class TickerPair {
        private final String name;
        private final long id;

        private TickerPair(String name, long id) {
            this.name = name;
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public List<TickerPair> getTickers() {

        SqlRowSet rowSet = jdbcTemplate.getJdbcOperations().queryForRowSet("SELECT * FROM Ticker ORDER BY Id");

        List<TickerPair> tickers = new LinkedList();

        while(rowSet.next()) {
            String name = rowSet.getString("TickerName");
            Long id = rowSet.getLong("Id");
            tickers.add(new TickerPair(name, id));
        }
        return tickers;
    }
}
