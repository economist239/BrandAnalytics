package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

/**
 * Created by IntelliJ IDEA.
 * User: alexsen
 * Date: 18.02.12
 * Time: 18:03
 */
public class GetLatestMentionsYalet extends AbstractDbYalet {

    @Override
    public void process(InternalRequest req, InternalResponse res) {
        res.add(providersHandler.getMentionProvider().getLatestMentions());
    }
}
