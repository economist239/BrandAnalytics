package ru.brandanalyst.core.db.provider.mysql;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.interfaces.MentionProvider;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.model.Mention;
import ru.brandanalyst.core.model.SingleDot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alexsen
 * Date: 23.02.12
 * Time: 20:44
 */
public class MySQLMentionProvider implements MentionProvider{
    
    private SimpleJdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Required
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Required
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Mention> getLatestMentions() {
        final List<Mention> mentionList = new ArrayList<Mention>();
        namedParameterJdbcTemplate.query(
                "SELECT * FROM Graphs"
                + " INNER JOIN Ticker ON TickerId = Ticker.Id"
                + " INNER JOIN Brand ON BrandId = Brand.Id"
                + " AND Tstamp = (SELECT MAX(Tstamp) FROM Graphs) ORDER BY TickerId DESC",
                new MapSqlParameterSource(),
                new RowCallbackHandler() {
                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        String tickerName = rs.getString("TickerName");
                        int tickerId = rs.getInt("TickerId");
                        int brandId = rs.getInt("BrandId");
                        String brand = rs.getString("Name");
                        SingleDot dot = new SingleDot(null,rs.getDouble("Val"));
                        Mention m = new Mention(dot,tickerName,brand,tickerId,brandId);
                        mentionList.add(m);
                    }
                });

        return mentionList;
    }
}
