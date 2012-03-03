package ru.brandanalyst.db;

import ru.brandanalyst.core.db.provider.interfaces.TickerProvider;
import ru.brandanalyst.core.model.TickerPair;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dima
 * Date: 2/26/12
 * Time: 6:41 PM
 */
public class InMemoryTickerProvider implements TickerProvider {
    List<TickerPair> tickerPairs = new ArrayList<TickerPair>();

    {
        tickerPairs.add(new TickerPair("1", 1));
        tickerPairs.add(new TickerPair("2", 2));
    }

    @Override
    public List<TickerPair> getTickers() {
        return tickerPairs;
    }
}
