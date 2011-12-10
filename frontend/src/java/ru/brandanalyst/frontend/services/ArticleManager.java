package ru.brandanalyst.frontend.services;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.mysql.MySQLArticleProvider;
import ru.brandanalyst.core.db.provider.mysql.MySQLInformationSourceProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.InfoSource;
import ru.brandanalyst.core.model.simple.WideArticleForWeb;

/**
 * Сервис, извлекающий новости по их идентификатору из БД
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/25/11
 * Time: 11:41 PM
 * article service (get article from db and push it to yalet)
 */
public class ArticleManager {

    private final SimpleJdbcTemplate jdbcTemplate;

    public ArticleManager(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public WideArticleForWeb getArticle(long id) {
        MySQLArticleProvider articleProvider = new MySQLArticleProvider(jdbcTemplate);
        Article article = articleProvider.getArticleById(id);

        MySQLInformationSourceProvider informationSourceProvider = new MySQLInformationSourceProvider(jdbcTemplate);
        InfoSource infoSource = informationSourceProvider.getInfoSourceById(article.getSourceId());

        return new WideArticleForWeb(article.getLink(),
                article.getTitle(),
                article.getContent(),
                infoSource.getTitle(),
                infoSource.getWebsite(),
                article.getTstamp().toString());
    }
}
