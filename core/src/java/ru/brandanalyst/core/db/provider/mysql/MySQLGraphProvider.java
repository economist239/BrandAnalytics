package ru.brandanalyst.core.db.provider.mysql;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.model.SingleDot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/16/11
 * Time: 7:54 PM
 * this class provides graphs from DB
 */
public class MySQLGraphProvider implements GraphProvider {
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
    public void writeGraph(final Graph graph, final long brandId, final long tickerId) {
        final Iterator<SingleDot> it = graph.getGraph().iterator();
        jdbcTemplate.update("INSERT INTO Graphs (BrandId, TickerId, Tstamp, Val) VALUES(?,?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                SingleDot d = it.next();
                ps.setLong(1, brandId);
                ps.setLong(2, tickerId);
                ps.setTimestamp(3, d.getDate());
                ps.setDouble(4, d.getValue());
            }

            @Override
            public int getBatchSize() {
                return graph.getGraph().size();
            }
        });
    }

    @Override
    public Graph getGraphByTickerAndBrand(long brandId, long tickerId) {
        final Graph graph = new Graph();
        jdbcTemplate.getJdbcOperations().query("SELECT * FROM Graphs INNER JOIN Ticker ON TickerId = Ticker.Id WHERE BrandId =? AND TickerId=?", new Object[]{brandId, tickerId}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                if (rs.isFirst()) {
                    graph.setTicker(rs.getString("TickerName"));
                }
                SingleDot d = new SingleDot(rs.getTimestamp("Tstamp"), rs.getDouble("Val"));
                graph.addPoint(d);
            }
        });

        return graph;
    }

    @Override
    public List<Graph> getGraphByTickerAndBrand(long brandId, List<Long> tickerIds) {
        final List<Graph> graphList = new ArrayList<Graph>(tickerIds.size());
        namedParameterJdbcTemplate.query("SELECT * FROM Graphs INNER JOIN Ticker ON TickerId = Ticker.Id"
                + " WHERE BrandId=:b_id AND TickerID IN :t_ids ORDER BY TickerId",
                new MapSqlParameterSource("b_id", brandId).addValue("t_ids", tickerIds),
                new RowCallbackHandler() {
                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        String tickerName = rs.getString("TickerName");
                        Graph g;
                        if (graphList.size() == 0 || !graphList.get(graphList.size() - 1).getTicker().equals(tickerName)) {
                            g = new Graph();
                            g.setTicker(tickerName);
                            graphList.add(g);
                        } else {
                            g = graphList.get(graphList.size() - 1);
                        }
                        g.addPoint(new SingleDot(rs.getTimestamp("Tstamp"), rs.getDouble("Val")));
                    }
                });

        return graphList;
    }

    @Override
    public List<Graph> getGraphsByBrandId(long brandId) {
        final List<Graph> graphList = new ArrayList<Graph>();
        jdbcTemplate.getJdbcOperations().query("SELECT * FROM Graphs INNER JOIN Ticker ON TickerId = Ticker.Id WHERE BrandId =? ORDER BY TickerId", new Object[]{brandId}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                String tickerName = rs.getString("TickerName");
                Graph g;
                if (graphList.size() == 0 || !graphList.get(graphList.size() - 1).getTicker().equals(tickerName)) {
                    g = new Graph();
                    g.setTicker(tickerName);
                    graphList.add(g);
                } else {
                    g = graphList.get(graphList.size() - 1);
                }
                g.addPoint(new SingleDot(rs.getTimestamp("Tstamp"), rs.getDouble("Val")));
            }
        });

        return graphList;
    }
}
