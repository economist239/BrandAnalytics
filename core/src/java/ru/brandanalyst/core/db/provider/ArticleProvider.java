package ru.brandanalyst.core.db.provider;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.db.mapper.ArticleMapper;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 1
 * Date: 09.10.11
 * Time: 22:07
 * To change this template use File | Settings | File Templates.
 */
public class ArticleProvider {
    private SimpleJdbcTemplate jdbcTemplate;
    private ArticleMapper articleMapper;

    public ArticleProvider(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        articleMapper = new ArticleMapper();
    }

    public void cleanDataStore() {
        jdbcTemplate.update("TRUNCATE TABLE Article");
    }

    public void writeArticleToDataStore(Article article) {
        try {
            jdbcTemplate.update("INSERT INTO Article (InfoSourceId, BrandId, Title, Content, Link, NumLikes, Tstamp) VALUES(?, ?, ?, ?, ?, ?, ?);", article.getBrandId(),
            article.getSourceId() ,article.getTitle(),article.getContent(),article.getLink(),article.getNumLikes(),article.getTstamp());
        } catch (Exception e) {e.printStackTrace();}
    }

    public void writeListOfArticlesToDataStore(List<Article> articles) {
        for (Article a : articles) {
            writeArticleToDataStore(a);
        }
    }

    public Article getArticleBySourceId(int sourceId) {
        List<Article> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Article WHERE InfosourceId = ?", new Object[]{sourceId}, articleMapper);
        return list.get(0);
    }

    public Article getArticleById(int article_id) {
        List<Article> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Article WHERE Id = " + Integer.toString(article_id) , articleMapper);
        return list.get(0);
    }

    public List<Article> getAllArticles() {
        List<Article> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Article", articleMapper);
        return list;
    }
}
