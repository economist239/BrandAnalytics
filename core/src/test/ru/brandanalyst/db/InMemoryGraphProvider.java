package ru.brandanalyst.db;

import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.model.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dima
 * Date: 2/26/12
 * Time: 6:19 PM
 */
public class InMemoryGraphProvider implements GraphProvider {
    @Override
    public void writeGraph(Graph graph, long brandId, long tickerId) {

    }

    @Override
    public Graph getGraphByTickerAndBrand(long brandId, long tickerId) {
        return new Graph();
    }

    @Override
    public List<Graph> getGraphByTickerAndBrand(long brandId, List<Long> tickerIds) {
        return new ArrayList<Graph>();
    }

    @Override
    public List<Graph> getGraphsByBrandId(long brandId) {
        return new ArrayList<Graph>();
    }
}
