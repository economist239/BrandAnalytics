package ru.brandanalyst.core.model;

import java.sql.Timestamp;

/**
 * Модель новостной статьи
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

    /**
     * @param id       идентефикатор статьи
     * @param brandId  индетификатор бренда, о котором данная статья
     * @param sourceId идентификатор источника
     * @param title    название статьи
     * @param content  такст статьи
     * @param link     ссылка на статью
     * @param tstamp   время публикования
     * @param numLikes количество лайков
     */
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

    /**
     * @param brandId  индетификатор бренда, о котором данная статья
     * @param sourceId идентификатор источника
     * @param title    название статьи
     * @param content  такст статьи
     * @param link     ссылка на статью
     * @param tstamp   время публикования
     * @param numLikes количество лайков
     */
    public Article(long brandId, long sourceId, String title, String content, String link, Timestamp tstamp, int numLikes) {
        this.id = (long) -1;
        this.sourceId = sourceId;
        this.title = title;
        this.content = content;
        this.link = link;
        this.tstamp = tstamp;
        this.numLikes = numLikes;
        this.brandId = brandId;
    }

    /**
     * @return идентификатор статьи
     */
    public long getId() {
        return id;
    }

    /**
     * @return идентификатор источника
     */
    public long getSourceId() {
        return sourceId;
    }

    /**
     * @return идентификатор бренда, о котором данная статья
     */
    public long getBrandId() {
        return brandId;
    }

    /**
     * @return название статьи
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return содержание статьи
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return ссылка на статью
     */
    public String getLink() {
        return link;
    }

    /**
     * @return дата статьи
     */
    public Timestamp getTstamp() {
        return tstamp;
    }

    /**
     * @return количество лайков
     */
    public int getNumLikes() {
        return numLikes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (!(o instanceof Article)) {
            return false;    
        }
        
        Article that = (Article) o;
        return that.content.equals(this.content) && that.brandId == this.brandId 
                && that.title.equals(this.title) && that.tstamp.equals(this.tstamp)
                && that.sourceId == this.sourceId;
        
    }

    @Override
    public int hashCode() {
        int hash = content.hashCode();
        hash += 31 * title.hashCode() + 7;
        hash += 31 * brandId + 7;
        hash += 31 * sourceId + 7;
        hash += 31 * tstamp.hashCode() + 7;
        return hash;
    }
}
