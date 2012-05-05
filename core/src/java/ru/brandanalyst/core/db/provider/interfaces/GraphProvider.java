package ru.brandanalyst.core.db.provider.interfaces;


import ru.brandanalyst.core.model.Graph;

import java.util.List;
import java.util.Map;

public interface GraphProvider {
    void writeGraph(Graph graph, long brandId, long tickerId);

    Graph getGraphByTickerAndBrand(long brandId, long tickerId);

    List<Graph> getGraphByTickerAndBrand(long brandId, List<Long> tickerIds);

    List<Graph> getGraphsByBrandId(long brandId);

    Map<Long, Graph> getGraphsByTickerId(long tickerId);
}
