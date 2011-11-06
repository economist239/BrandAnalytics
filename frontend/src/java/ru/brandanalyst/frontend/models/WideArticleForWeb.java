package ru.brandanalyst.frontend.models;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/26/11
 * Time: 12:20 AM
 * wide model of article for web
 */
public final class WideArticleForWeb {
    private final String link;
    private final String title;
    private final String content;
    private final String sourceName;
    private final String sourceLink;
    private final String time;

    public WideArticleForWeb(String link, String title, String content, String sourceName, String sourceLink, String time) {
        this.link = link;
        this.title = title;
        this.content = content;
        this.sourceName = sourceName;
        this.sourceLink = sourceLink;
        this.time = time.substring(0,10);
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public String getTime() {
        return time;
    }
}
