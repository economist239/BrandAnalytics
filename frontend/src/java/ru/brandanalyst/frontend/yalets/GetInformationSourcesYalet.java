package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

/**
 * Created by IntelliJ IDEA.
 * User: alexsen
 * Date: 03.03.12
 * Time: 12:35
 */
public class GetInformationSourcesYalet extends AbstractDbYalet {
    @Override
    public void process(InternalRequest req, InternalResponse res) {
        res.add(providersHandler.getInformationSourceProvider().getAllInfoSources());
    }
}
