package ru.brandanalyst.core.model;

/**
 * Created by IntelliJ IDEA.
 * User: 1
 * Date: 09.10.11
 * Time: 21:28
 * To change this template use File | Settings | File Templates.
 */
public class Mention {
    private int brandId;
    private long articleId;
    private double mention;

    public Mention(int brandId, long articleId, double mention) {
        this.brandId = brandId;
        this.articleId = articleId;
        this.mention = mention;
    }
    public int getBrandId() {
        return brandId;
    }

    public void setId(int brandId) {
        this.brandId = brandId;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public double getMention() {
        return mention;
    }

    public void setMention(double description) {
        this.mention = mention;
    }
}
