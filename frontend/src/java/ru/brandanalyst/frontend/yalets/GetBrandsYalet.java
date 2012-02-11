package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

/**
 * Ялет, показывающий левое меню
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/25/11
 * Time: 11:40 PM
 */
public class GetBrandsYalet extends AbstractDbYalet {

    public void process(InternalRequest req, InternalResponse res) {
        res.add(providersHandler.getBrandProvider().getAllBrands());
    }
}
