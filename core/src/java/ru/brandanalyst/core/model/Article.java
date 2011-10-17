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
    private long sourceId;
    private String title;
    private String content;
    private String link;
    private String tstamp;
    private int numLikes;

    public Article(long id, long sourceId, String title, String content, String link, String tstamp, int numLikes) {
        this.id = id;
        this.sourceId = sourceId;
        this.title = title;
        this.content = content;
        this.link = link;
        this.tstamp = tstamp;
        this.numLikes = numLikes;
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

    public String getLink() {
        return link;
    }

    public void setAuthor(String link) {
        this.link = link;
    }

    public String getTstamp() {
        return tstamp;
    }

    public void setTstamp(String tstamp) {
        this.tstamp = tstamp;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }
}
