package ru.brandanalyst.frontend.models;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/19/11
 * Time: 8:42 PM
 */
public class YouTubeVideo {
    private final String title;
    private final String url;
    private final String playerCode;

    public YouTubeVideo(String title, String url, String playerCode) {
        this.title = title;
        this.url = url;
        this.playerCode = playerCode;
    }

    public String getPlayerCode() {
        return playerCode;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
