package ru.brandanalyst.core.model;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 10/16/11
 * Time: 7:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Graph {
    private String ticker;
    private List<SingleDot> graph;

    public void Graph(String ticker, List<SingleDot> graph) {
        this.graph = graph;
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }

    public List<SingleDot> getGraph() {
        return graph;
    }
}
