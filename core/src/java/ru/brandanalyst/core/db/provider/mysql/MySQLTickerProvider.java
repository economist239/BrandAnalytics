package ru.brandanalyst.core.db.provider.mysql;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.interfaces.TickerProvider;
import ru.brandanalyst.core.model.TickerPair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/19/11
 * Time: 2:46 PM
 */
public class MySQLTickerProvider implements TickerProvider {
    private SimpleJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TickerPair> getTickers() {
        final List<TickerPair> tickers = new LinkedList();

        jdbcTemplate.getJdbcOperations().query("SELECT * FROM Ticker ORDER BY Id", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                String name = rs.getString("TickerName");
                Long id = rs.getLong("Id");
                tickers.add(new TickerPair(name, id));
            }
        });
        return tickers;
    }
}
