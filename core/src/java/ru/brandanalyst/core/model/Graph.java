package ru.brandanalyst.core.model;

import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.brandanalyst.core.util.Jsonable;
import ru.brandanalyst.core.util.Time;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/16/11
 * Time: 7:18 PM
 * general model of graph
 */
public class Graph implements Jsonable {
    private Map<LocalDate, Double> graph;
    private String ticker;

    public Graph() {
        this(new LinkedList<SingleDot>());
    }

    public Graph(List<SingleDot> graph) {
        this.graph = new HashMap<LocalDate, Double>();
        for (SingleDot d: graph) {
            this.graph.put(d.getDate(), d.getValue());
        }
        this.ticker = "no ticker";
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Graph(String ticker) {
        graph = new HashMap<LocalDate, Double>();
        this.ticker = ticker;
    }

    public List<SingleDot> getGraph() {
        List<SingleDot> g = new ArrayList<SingleDot>(graph.size());
        for (Map.Entry<LocalDate, Double> d: graph.entrySet()) {
            g.add(new SingleDot(d.getKey(), d.getValue()));
        }
        return g;
    }

    public String getTicker() {
        return ticker;
    }

    public void addPoint(SingleDot dot) {
        addPoint(dot.getDate(), dot.getValue());
    }

    public void addPoint(LocalDate date, double value) {
        if (graph.containsKey(date)) {
            graph.put(date, graph.get(date) + value);
        } else {
            graph.put(date, value);
        }
    }

    @Override
    public JSONObject asJson() {
        //count millisecs in sec
        final long COUNT = 1000;
        try {
            JSONArray da = new JSONArray();
            Set<SingleDot> sortedGraph = new TreeSet<SingleDot>(getGraph());

            for (SingleDot d : sortedGraph) {
                da.put(new JSONArray().put(d.getDate().toDate().getTime() / COUNT).put(d.getValue()));
            }
            return new JSONObject().put("ticker", ticker).put("graph", da);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
