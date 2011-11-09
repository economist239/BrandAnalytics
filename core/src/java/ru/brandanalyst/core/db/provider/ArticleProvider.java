package ru.brandanalyst.core.db.provider;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.mapper.ArticleMapper;
import ru.brandanalyst.core.model.Article;

import java.util.List;

/**
 * Класс, предоставляющий доступ к новостям в БД
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 09.10.11
 * Time: 22:07
 */
public class ArticleProvider {
    private static final Logger log = Logger.getLogger(ArticleProvider.class);
    /**
     * максимальная длина новости, записываемой в БД (если новость длиннее, то она обрезается)
     */
    private static final int MAX_ARTCILE_LENGHT = 30000;

    private SimpleJdbcTemplate jdbcTemplate;
    /**
     * разметчик для преобразования строки в переменную типа Article
     */
    private ArticleMapper articleMapper;

    public ArticleProvider(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        articleMapper = new ArticleMapper();
    }

    @Deprecated
    public void cleanDataStore() {
        jdbcTemplate.update("TRUNCATE TABLE Article");
    }

    public void writeArticleToDataStore(Article article) {

        if (article.getContent().length() > MAX_ARTCILE_LENGHT) {
            article.setContent(article.getContent().substring(0, MAX_ARTCILE_LENGHT));
        }
        try {
            jdbcTemplate.update("INSERT INTO Article (InfoSourceId, BrandId, Title, Content, Link, NumLikes, Tstamp) VALUES(?, ?, ?, ?, ?, ?, ?);", article.getSourceId(),
                    article.getBrandId(), article.getTitle(), article.getContent(), article.getLink(), article.getNumLikes(), article.getTstamp());
        } catch (Exception e) {
            //    e.printStackTrace();
            //    System.out.println(article.getContent().length());
            log.error("cannot write article to db", e);
        }
    }

    public void writeListOfArticlesToDataStore(List<Article> articles) {
        for (Article a : articles) {
            writeArticleToDataStore(a);
        }
    }

    public Article getArticleBySourceId(long sourceId) {
        List<Article> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Article WHERE InfosourceId = " + sourceId, articleMapper);
        return list.get(0);
    }


    public List<Article> getAllArticlesBySourceId(long sourceId) {
        List<Article> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Article WHERE InfosourceId = " + sourceId, articleMapper);
        return list;
    }

    public Article getArticleById(long articleId) {
        List<Article> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Article WHERE Id = " + Long.toString(articleId), articleMapper);
        return list.get(0);
    }

    public List<Article> getAllArticlesByBrand(long brandId) {
        List<Article> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Article WHERE BrandId = " + Long.toString(brandId), articleMapper);
        return list;
    }

    public List<Article> getAllArticles() {
        List<Article> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Article", articleMapper);
        return list;
    }

    /**
     * возращает указанное количество самых свежих новостей по данном бренду
     */
    public List<Article> getTopArticles(long brandId, int topSize) {
        List<Article> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Article WHERE brandId = " + brandId + " ORDER BY Tstamp DESC LIMIT " + topSize, articleMapper);
        return list;
    }
}
