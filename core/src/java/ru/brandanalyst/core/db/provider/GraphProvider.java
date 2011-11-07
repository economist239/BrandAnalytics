package ru.brandanalyst.core.db.provider;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.model.SingleDot;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/16/11
 * Time: 7:54 PM
 * this class provides graphs from DB
 */
public class GraphProvider {
    private static final Logger log = Logger.getLogger(GraphProvider.class);

    private SimpleJdbcTemplate jdbcTemplate; //

    public GraphProvider(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void cleanDataStore() {
        jdbcTemplate.update("TRUNCATE TABLE Graphs");
    }

    public void writeGraph(Graph graph, long brandId, long tickerId) {
        for (SingleDot d : graph.getGraph()) {
            writeSingleDot(d, brandId, tickerId);
        }
    }

    public void writeSingleDot(SingleDot dot, long brandId, long tickerId) {
        writeSingleDot(dot.getDate(), dot.getValue(), brandId, tickerId);
    }

    public void writeSingleDot(Timestamp Tstamp, double value, long brandId, long tickerId) {
        try {
            jdbcTemplate.update("INSERT INTO Graphs (BrandId, TickerId, Tstamp, Val) VALUES(?,?,?,?);", brandId, tickerId,
                    Tstamp, value);
        } catch (Exception e) {
            log.error("cannot write dot to db");
        }
    }

    public List<Graph> getGraphsByBrandId(long brandId) {

        SqlRowSet rowSet = jdbcTemplate.getJdbcOperations().queryForRowSet("SELECT * FROM Graphs INNER JOIN Ticker ON TickerId = Ticker.Id WHERE BrandId = " + Long.toString(brandId) + " ORDER BY TickerId");

        List<Graph> graphList = new ArrayList<Graph>();

        try {
            if (rowSet.next()) {
                String tickerName = rowSet.getString("TickerName");
                graphList.add(new Graph(tickerName));
            } else {
                //    System.out.println("asdfghjkl;");
                return null;
            }

            int curGraph = 0;
            do {
                Timestamp curTstamp = rowSet.getTimestamp("Tstamp");
                double curValue = rowSet.getDouble("Val");
                String curTicker = rowSet.getString("TickerName");
                if (curTicker.indexOf(graphList.get(curGraph).getTicker()) != 0) {
                    Graph graph = new Graph(curTicker);
                    graphList.add(graph);
                    curGraph++;
                }
                graphList.get(curGraph).addPoint(new SingleDot(curTstamp, curValue));
            } while (rowSet.next());
            //System.out.println(graphList.size()+"asdzxc");
            return graphList;
        } catch (Exception e) {
            //            System.out.println("asd980980980");
            log.error("can't get graphs from db");
            return null;
        }
    }
}
