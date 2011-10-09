package ru.brandanalyst.core.model;

/**
 * Created by IntelliJ IDEA.
 * User: 1
 * Date: 09.10.11
 * Time: 20:32
 * To change this template use File | Settings | File Templates.
 */
public class Article {
    private long id;
    private int sourceId;
    private String title;
    private String content;
    private String author;
    private String timestamp;

    public Article(int id, int sourceId, String title, String content, String author, String timestamp) {
        this.id = id;
        this.sourceId = sourceId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.timestamp = timestamp;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimesamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
