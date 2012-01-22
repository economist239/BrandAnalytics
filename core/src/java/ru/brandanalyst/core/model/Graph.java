package ru.brandanalyst.core.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.brandanalyst.core.util.Jsonable;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/16/11
 * Time: 7:18 PM
 * general model of graph
 */
public class Graph implements Jsonable {
    private List<SingleDot> graph;
    private String ticker;

    public Graph() {
        this(new LinkedList<SingleDot>());
    }

    public Graph(List<SingleDot> graph) {
        this.graph = graph;
        this.ticker = "no ticker";
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Graph(String ticker) {
        graph = new ArrayList<SingleDot>();
        this.ticker = ticker;
    }

    public List<SingleDot> getGraph() {
        return graph;
    }

    public String getTicker() {
        return ticker;
    }

    public void addPoint(SingleDot dot) {
        graph.add(dot);
    }

    @Override
    public JSONObject asJson() {
        //count millisecs in sec
        final long COUNT = 1000;
        try {
            JSONArray da = new JSONArray();
            Set<SingleDot> sortedGraph = new TreeSet<SingleDot>(graph);

            for (SingleDot d : sortedGraph) {
                da.put(new JSONArray().put(d.getDate().getTime() / COUNT).put(d.getValue()));
            }
            return new JSONObject().put("ticker", ticker).put("graph", da);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
