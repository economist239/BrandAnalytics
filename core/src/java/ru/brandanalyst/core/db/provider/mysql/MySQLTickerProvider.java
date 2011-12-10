package ru.brandanalyst.core.db.provider.mysql;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.brandanalyst.core.db.provider.global.TickerProvider;
import ru.brandanalyst.core.model.TickerPair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/19/11
 * Time: 2:46 PM
 */
public class MySQLTickerProvider implements TickerProvider {
    private static final Logger log = Logger.getLogger(MySQLGraphProvider.class);

    private SimpleJdbcTemplate jdbcTemplate; //

    public MySQLTickerProvider(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
