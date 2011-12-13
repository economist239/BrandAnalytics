package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import ru.brandanalyst.frontend.services.YouTubeManager;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/19/11
 * Time: 8:29 PM
 */
public class ShowYouTubeYalet extends AbstractDbYalet {
     public void process(InternalRequest req, InternalResponse res) {

        long brandId = req.getLongParameter("id");

        YouTubeManager manager = new YouTubeManager(providersHandler);
        res.add(manager.getVideosByBrandId(brandId));
    }
}
