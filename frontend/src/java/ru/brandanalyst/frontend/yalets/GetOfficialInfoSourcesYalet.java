package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

/**
 * @author daddy-bear
 *         Date: 03.03.12
 */
public class GetOfficialInfoSourcesYalet extends AbstractDbYalet {
    @Override
    public void process(InternalRequest req, InternalResponse res) {
        res.add(providersHandler.getInformationSourceProvider().getAllInfoSources());
    }
}
