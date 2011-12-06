package ru.brandanalyst.frontend.services;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.mysql.MySQLBrandProvider;
import ru.brandanalyst.frontend.models.YouTubeVideo;
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
public class YouTubeManager {
    private final SimpleJdbcTemplate jdbcTemplate;

    public YouTubeManager(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<YouTubeVideo> getVideosByBrandId(long brandId) {

        String brandName = new MySQLBrandProvider(jdbcTemplate).getBrandById(brandId).getName();

        try {
            ArrayList<YouTubeEntry> result = YouTubeGrabber.searchYouTubeVideos(brandName, 10);
            List<YouTubeVideo> videos = new LinkedList();
            for(YouTubeEntry e: result) {
                videos.add(e.toVideoModel());
            }
            return videos;
        } catch (YouTubeGrabberException e) {
            return null;
        }


    }
}
