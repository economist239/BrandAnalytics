package ru.brandanalyst.frontend.models;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/20/11
 * Time: 12:54 PM
 */
public class SimplyArticleForWeb {
    private String name;
    private long id;
    private String shortContent;
    private String sourceName;
    private String sourceLink;

    public SimplyArticleForWeb(String name, long id, String shortContent, String sourceName, String sourceLink) {
        this.name = name;
        this.id = id;
        this.shortContent = shortContent;
        this.sourceLink = sourceLink;
        this.sourceName = sourceName;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getShortContent() {
        return shortContent;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getSourceLink() {
        return sourceLink;
    }
}