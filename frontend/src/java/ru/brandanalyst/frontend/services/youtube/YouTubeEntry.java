package ru.brandanalyst.frontend.services.youtube;

import ru.brandanalyst.frontend.models.YouTubeVideo;

public class YouTubeEntry {
	private String href;
	private String videoUrlForPlayer;
	private String title;

    private final static int PLAYER_WIDTH = 480;
    private final static int PLAYER_HEIGHT = 360;

	public YouTubeEntry(String href, String videoUrlForPlayer, String title) {
		if (href == null || href.length() == 0
				|| videoUrlForPlayer == null || videoUrlForPlayer.length() == 0
				|| title == null || title.length() == 0) {
			throw new IllegalArgumentException("YouTubeEntry data is empty");
		}

		this.href = href;
		this.videoUrlForPlayer = videoUrlForPlayer;
		this.title = title;
	}

    public YouTubeVideo toVideoModel() {
        return new YouTubeVideo(title, href, videoUrlForPlayer);
    }

	public String getHref() {
		return href;
	}

	public String getEmbeddedPlayerHtmlCode() {
		String code = "<object style=\"height: " + PLAYER_HEIGHT + "px; width: " + PLAYER_WIDTH + "px\">\n" +
				"<param name=\"movie\" value=\""+videoUrlForPlayer+"&feature=player_embedded\">\n" +
				"<param name=\"allowFullScreen\" value=\"true\">\n" +
				"<param name=\"allowScriptAccess\" value=\"always\">\n" +
				"<embed src=\""+videoUrlForPlayer+"&feature=player_embedded\" " +
				"type=\"application/x-shockwave-flash\" allowfullscreen=\"true\" allowScriptAccess=\"always\"" +
				" width=\"" + PLAYER_WIDTH + "\" height=\"" + PLAYER_HEIGHT + "\"></object>";
		return code;
	}

	public String getTitle() {
		return title;
	}
}
