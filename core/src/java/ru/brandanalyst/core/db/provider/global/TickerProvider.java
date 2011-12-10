package ru.brandanalyst.core.db.provider.global;

import ru.brandanalyst.core.model.TickerPair;

import java.util.List;

public interface TickerProvider {

    public List<TickerPair> getTickers();
}
