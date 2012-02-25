package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Obus
 * Date: 17.02.12
 * Time: 19:48
 * To change this template use File | Settings | File Templates.
 */
public class GetBranchesYalet extends AbstractDbYalet{
    @Override
    public void process(InternalRequest req, InternalResponse res) {
        res.add(providersHandler.getBranchesProvider().getAllBranches());
    }
}
