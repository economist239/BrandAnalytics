package ru.brandanalyst.frontend.services;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.db.provider.ArticleProvider;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/25/11
 * Time: 11:41 PM
 */
public class ArticleManager {

    private final SimpleJdbcTemplate jdbcTemplate;

    public ArticleManager(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Article getArticle(long id) {
        ArticleProvider articleProvider = new ArticleProvider(jdbcTemplate);
        return articleProvider.getArticleById(id);
    }
}
