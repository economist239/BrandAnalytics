package ru.brandanalyst.core.model;

import java.sql.Timestamp;
/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 09.10.11
 * Time: 20:32
 */
public class Article {
    private long id;
    private long brandId;
    private long sourceId;
    private String title;
    private String content;
    private String link;
    private Timestamp tstamp;
    private int numLikes;

    public Article(long id, long brandId, long sourceId, String title, String content, String link, Timestamp tstamp, int numLikes) {
        this.id = id;
        this.sourceId = sourceId;
        this.title = title;
        this.content = content;
        this.link = link;
        this.tstamp = tstamp;
        this.numLikes = numLikes;
        this.brandId = brandId;
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

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.sourceId = brandId;
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

    public Timestamp getTstamp() {
        return tstamp;
    }

    public void setTstamp(Timestamp tstamp) {
        this.tstamp = tstamp;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }
}
