package ru.brandanalyst.core.db.provider.interfaces;

import ru.brandanalyst.core.db.provider.EntityVisitor;
import ru.brandanalyst.core.db.provider.mysql.MappersHolder;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.ArticleForWeb;

import java.util.List;

public abstract class ArticleProvider {
    protected final static int MAX_ARTICLE_LENGHT = 30000;

    public abstract void writeArticleToDataStore(Article article);

    public abstract void writeListOfArticlesToDataStore(List<Article> articles);

    public abstract Article getArticleBySourceId(long sourceId);

    public abstract List<Article> getAllArticlesBySourceId(long sourceId);

    public abstract Article getArticleById(long articleId);

    public abstract ArticleForWeb getArticleForWebById(long articleId);

    public abstract List<Article> getAllArticlesByBrand(long brandId);

    public abstract List<Article> getAllOfficialArticlesByBrand(long brandId);

    public abstract List<Article> getAllArticles();

    public abstract List<Article> getArticlesWithCondition(String whereClause);

    public abstract void visitArticles(EntityVisitor<Article> visitor);
    /*
     * возращает указанное количество самых свежих новостей по данном бренду
     */
    public abstract List<Article> getTopArticles(long brandId, int topSize);

    public abstract List<Article> getAllArticlesByBrandAndSource(long brandId, long sourceId);

    /*
    *
    * only for dirty db!!
    */
    public abstract void setAnalyzed(final List<Long> ids);

    public abstract List<Article> getOnlyNotAnalyzedArticles();
}
