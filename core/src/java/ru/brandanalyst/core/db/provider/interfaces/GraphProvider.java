package ru.brandanalyst.core.db.provider.interfaces;


import ru.brandanalyst.core.model.Graph;

import java.util.List;

public interface GraphProvider {
    public void writeGraph(Graph graph, long brandId, long tickerId);

    public Graph getGraphByTickerAndBrand(long brandId, long tickerId);

    public List<Graph> getGraphByTickerAndBrand(long brandId, List<Long> tickerIds);

    public List<Graph> getGraphsByBrandId(long brandId);


}
