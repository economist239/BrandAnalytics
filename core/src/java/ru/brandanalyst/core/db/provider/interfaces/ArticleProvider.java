package ru.brandanalyst.core.db.provider.interfaces;

import ru.brandanalyst.core.model.Article;

import java.util.List;

public interface ArticleProvider {
    int MAX_ARTICLE_LENGHT = 30000;

    public void writeArticleToDataStore(Article article);

    public void writeListOfArticlesToDataStore(List<Article> articles);

    public Article getArticleBySourceId(long sourceId);


    public List<Article> getAllArticlesBySourceId(long sourceId);

    public Article getArticleById(long articleId);

    public List<Article> getAllArticlesByBrand(long brandId);

    public List<Article> getAllOfficialArticlesByBrand(long brandId);

    public List<Article> getAllArticles();

    /**
     * возращает указанное количество самых свежих новостей по данном бренду
     */
    public List<Article> getTopArticles(long brandId, int topSize);

    public List<Article> getAllArticlesByBrandAndSource(long brandId, long sourceId);
}
