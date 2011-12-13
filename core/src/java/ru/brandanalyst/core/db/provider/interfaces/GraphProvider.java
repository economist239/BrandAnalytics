package ru.brandanalyst.core.db.provider.interfaces;


import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.model.SingleDot;

import java.sql.Timestamp;
import java.util.List;

public interface GraphProvider {

    public void cleanDataStore();

    public void writeGraph(Graph graph, long brandId, long tickerId);

    public void writeSingleDot(SingleDot dot, long brandId, long tickerId);

    public void writeSingleDot(Timestamp Tstamp, double value, long brandId, long tickerId);

    public Graph getGraphByTickerAndBrand(long brandId, long tickerId);

    public List<Graph> getGraphsByBrandId(long brandId);
}
