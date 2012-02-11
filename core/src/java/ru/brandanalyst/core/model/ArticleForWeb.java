package ru.brandanalyst.core.model;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/26/11
 * Time: 12:20 AM
 * wide model of article for web
 */
public final class ArticleForWeb {
    private final String link;
    private final String title;
    private final String content;
    private final String sourceName;
    private final String sourceLink;
    private final Date time;

    private static final int TIME_STRING_LENGTH = 10;

    public ArticleForWeb(String link, String title, String content, String sourceName, String sourceLink, Date time) {
        this.link = link;
        this.title = title;
        this.content = content;
        this.sourceName = sourceName;
        this.sourceLink = sourceLink;
        this.time = time;
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

    public Date getTime() {
        return time;
    }
}
