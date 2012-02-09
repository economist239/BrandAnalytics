package ru.brandanalyst.core.db.provider.interfaces;

import ru.brandanalyst.core.db.provider.mysql.MappersHolder;
import ru.brandanalyst.core.model.Article;

import java.util.List;

public interface ArticleProvider {
    int MAX_ARTICLE_LENGHT = 30000;

    void writeArticleToDataStore(Article article);

    void writeListOfArticlesToDataStore(List<Article> articles);

    Article getArticleBySourceId(long sourceId);


    List<Article> getAllArticlesBySourceId(long sourceId);

    Article getArticleById(long articleId);

    List<Article> getAllArticlesByBrand(long brandId);

    List<Article> getAllOfficialArticlesByBrand(long brandId);

    List<Article> getAllArticles();

    List<Article> getArticlesWithCondition(String whereClause);

    /*
     * возращает указанное количество самых свежих новостей по данном бренду
     */
    List<Article> getTopArticles(long brandId, int topSize);

    List<Article> getAllArticlesByBrandAndSource(long brandId, long sourceId);

    /*
    *
    * only for dirty db!!
    */
    void setAnalyzed(final List<Long> ids);

    List<Article> getOnlyNotAnalyzedArticles();
}
