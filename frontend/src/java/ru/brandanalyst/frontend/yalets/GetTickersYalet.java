package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/11/12
 * Time: 10:40 AM
 */
public class GetTickersYalet extends AbstractDbYalet {
    @Override
    public void process(InternalRequest req, InternalResponse res) {
        res.add(providersHandler.getTickerProvider().getTickers());
    }
}
