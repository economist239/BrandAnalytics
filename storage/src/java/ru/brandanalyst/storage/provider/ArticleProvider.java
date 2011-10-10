package ru.brandanalyst.storage.provider;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.storage.mapper.ArticleMapper;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 1
 * Date: 09.10.11
 * Time: 22:07
 * To change this template use File | Settings | File Templates.
 */
public class ArticleProvider {
    private SimpleJdbcTemplate jdbcTemplateDirty;
    private SimpleJdbcTemplate jdbcTemplatePure;
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
            jdbcTemplate.update("INSERT INTO Article (article_id, info_source_id, title, content, author, timestmp) VALUES(?, ?, ?, ?, ?, ?);", article.getId(), article.getSourceId(),
                article.getTitle(),article.getContent(),article.getAuthor(),article.getTimesamp());
        } catch (Exception e) {}
    }

    public void writeListOfArticlesToDataStore(List<Article> articles) {
        for (Article a : articles) {
            writeArticleToDataStore(a);
        }
    }

    public Article getArticleBySourceId(int sourceId) {
        List<Article> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM article WHERE info_source_id = ?", new Object[]{sourceId}, articleMapper);
        return list.get(0);
    }

    public Article getArticleById(int article_id) {
        List<Article> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM article WHERE article_id = " + Integer.toString(article_id) , articleMapper);
        return list.get(0);
    }
}
