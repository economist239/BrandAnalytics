package ru.brandanalyst.frontend.services.youtube;

public class YouTubeEntry {
    private String url;
    private String videoUrlForPlayer;
    private String title;

    private final static int PLAYER_WIDTH = 480;
    private final static int PLAYER_HEIGHT = 360;

    public YouTubeEntry(String url, String videoUrlForPlayer, String title) {
        if (url == null || url.length() == 0
                || videoUrlForPlayer == null || videoUrlForPlayer.length() == 0
                || title == null || title.length() == 0) {
            throw new IllegalArgumentException("YouTubeEntry data is empty");
        }

        this.url = url;
        this.videoUrlForPlayer = videoUrlForPlayer;
        this.title = title;
    }

    public String getPlayerCode() {
        return "<object style=\"height: " + PLAYER_HEIGHT + "px; width: " + PLAYER_WIDTH + "px\">\n" +
                "<param name=\"movie\" value=\"" + videoUrlForPlayer + "&feature=player_embedded\">\n" +
                "<param name=\"allowFullScreen\" value=\"true\">\n" +
                "<param name=\"allowScriptAccess\" value=\"always\">\n" +
                "<embed src=\"" + videoUrlForPlayer + "&feature=player_embedded\" " +
                "type=\"application/x-shockwave-flash\" allowfullscreen=\"true\" allowScriptAccess=\"always\"" +
                " width=\"" + PLAYER_WIDTH + "\" height=\"" + PLAYER_HEIGHT + "\"></object>";
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
