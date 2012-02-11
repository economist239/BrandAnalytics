package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/11/12
 * Time: 10:37 AM
 */
public class GetBrandYalet extends AbstractDbYalet {
    @Override
    public void process(InternalRequest req, InternalResponse res) {
        long brandId = req.getLongParameter("id");
        res.add(providersHandler.getBrandProvider().getBrandById(brandId));
    }
}
