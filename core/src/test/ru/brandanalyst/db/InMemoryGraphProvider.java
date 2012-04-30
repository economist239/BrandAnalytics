package ru.brandanalyst.db;

import org.joda.time.LocalDate;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.model.SingleDot;

import java.util.ArrayList;
import java.util.Calendar;
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
        return getRandomGraphByBrandId(brandId);
    }

    @Override
    public List<Graph> getGraphByTickerAndBrand(long brandId, List<Long> tickerIds) {
        ArrayList<Graph> result = new ArrayList<Graph>();
        for (int i = 0; i < tickerIds.size(); ++i) {
            result.add(getRandomGraphByBrandId(brandId));
        }
        return result;
    }

    @Override
    public List<Graph> getGraphsByBrandId(long brandId) {
        ArrayList<Graph> result = new ArrayList<Graph>();
        for (int i = 0; i < 4; ++i) {
            result.add(getRandomGraphByBrandId(brandId));
        }
        return result;
    }

    private Graph getRandomGraphByBrandId(long brandId) {
        int N = 100;
        Graph result = new Graph();
        List<SingleDot> values = new ArrayList<SingleDot>(N);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -N);
        for (int i = 0; i < N; ++i) {
            double value = brandId + Math.random();
            values.add(new SingleDot(new LocalDate(calendar.getTimeInMillis()), value));
            calendar.add(Calendar.DATE, 1);
        }

        return result;
    }
}
