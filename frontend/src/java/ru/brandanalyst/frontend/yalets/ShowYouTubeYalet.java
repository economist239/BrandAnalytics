package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import ru.brandanalyst.frontend.services.youtube.YouTubeGrabber;
import ru.brandanalyst.frontend.services.youtube.YouTubeGrabberException;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/19/11
 * Time: 8:29 PM
 */
public class ShowYouTubeYalet extends AbstractDbYalet {
    private final static int MAX_ENTRIES = 10;

    public void process(InternalRequest req, InternalResponse res) {
        long brandId = req.getLongParameter("id");
        String brandName = providersHandler.getBrandProvider().getBrandById(brandId).getName();
        try {
            res.add(YouTubeGrabber.searchYouTubeVideos(brandName, MAX_ENTRIES));
        } catch (YouTubeGrabberException e) {
            throw new RuntimeException(e);
        }
    }
}
