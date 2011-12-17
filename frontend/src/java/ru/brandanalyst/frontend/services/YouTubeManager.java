package ru.brandanalyst.frontend.services;

import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.model.YouTubeVideo;
import ru.brandanalyst.frontend.services.youtube.YouTubeEntry;
import ru.brandanalyst.frontend.services.youtube.YouTubeGrabber;
import ru.brandanalyst.frontend.services.youtube.YouTubeGrabberException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/19/11
 * Time: 8:30 PM
 */
public class YouTubeManager extends AbstractManager {

    public YouTubeManager(ProvidersHandler providersHandler) {
        super(providersHandler);
    }

    public List<YouTubeVideo> getVideosByBrandId(long brandId) {

        String brandName = providersHandler.getBrandProvider().getBrandById(brandId).getName();

        try {
            ArrayList<YouTubeEntry> result = YouTubeGrabber.searchYouTubeVideos(brandName, 10);
            List<YouTubeVideo> videos = new LinkedList();
            for (YouTubeEntry e : result) {
                videos.add(e.toVideoModel());
            }
            return videos;
        } catch (YouTubeGrabberException e) {
            return null;
        }


    }
}
