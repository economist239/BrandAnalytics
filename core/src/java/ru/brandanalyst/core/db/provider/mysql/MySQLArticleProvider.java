package ru.brandanalyst.core.db.provider.mysql;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.EntityVisitor;
import ru.brandanalyst.core.db.provider.interfaces.ArticleProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.ArticleForWeb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Класс, предоставляющий доступ к новостям в БД
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 09.10.11
 * Time: 22:07
 */
public class MySQLArticleProvider extends ArticleProvider {
    private SimpleJdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void writeArticleToDataStore(Article article) {

        if (article.getContent().length() > MAX_ARTICLE_LENGHT) {
            article.setContent(article.getContent().substring(0, MAX_ARTICLE_LENGHT));
        }
        jdbcTemplate.update("INSERT INTO Article (InfoSourceId, BrandId, Title, Content, Link, NumLikes, Tstamp) VALUES(?, ?, ?, ?, ?, ?, ?);", article.getSourceId(),
                article.getBrandId(), article.getTitle(), article.getContent(), article.getLink(), article.getNumLikes(), article.getTstamp());
    }

    @Override
    public void writeListOfArticlesToDataStore(final List<Article> articles) {
        final Iterator<Article> it = articles.iterator();
        jdbcTemplate.getJdbcOperations().batchUpdate("INSERT INTO Article (InfoSourceId, BrandId, Title, Content, Link, NumLikes, Tstamp) VALUES(?, ?, ?, ?, ?, ?, ?);", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Article a = it.next();
                ps.setLong(1, a.getSourceId());
                ps.setLong(2, a.getBrandId());
                ps.setString(3, a.getTitle().substring(0, MAX_TITLE_LENGTH));
                ps.setString(4, a.getContent().substring(0, MAX_ARTICLE_LENGHT));
                ps.setString(5, a.getLink());
                ps.setInt(6, a.getNumLikes());
                ps.setDate(7, new java.sql.Date(a.getTstamp().toDate().getTime()));
            }

            @Override
            public int getBatchSize() {
                return articles.size();
            }
        });
    }

    @Override
    public void visitArticles(final EntityVisitor<Article> visitor) {
        jdbcTemplate.getJdbcOperations().query("SELECT * FROM Article", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                visitor.visitEntity(MappersHolder.ARTICLE_MAPPER.mapRow(rs, 0));
            }
        });
    }

    @Override
    public Article getArticleBySourceId(long sourceId) {
        return jdbcTemplate.queryForObject("SELECT * FROM Article WHERE InfosourceId=?",
                MappersHolder.ARTICLE_MAPPER, sourceId);
    }

    @Override
    public List<Article> getAllArticlesBySourceId(long sourceId) {
        return jdbcTemplate.query("SELECT * FROM Article WHERE InfosourceId=?",
                MappersHolder.ARTICLE_MAPPER, sourceId);
    }

    @Override
    public Article getArticleById(long articleId) {
        return jdbcTemplate.queryForObject("SELECT * FROM Article WHERE Id=?",
                MappersHolder.ARTICLE_MAPPER, articleId);
    }

    @Override
    public ArticleForWeb getArticleForWebById(long articleId) {
        return jdbcTemplate.queryForObject("SELECT * FROM Article a, InformationSource s WHERE a.Id=? AND a.InfoSourceId=s.Id",
                MappersHolder.ARTICLE_FOR_WEB_MAPPER, articleId);
    }

    @Override
    public List<Article> getAllArticlesByBrand(long brandId) {
        return jdbcTemplate.query("SELECT * FROM Article WHERE BrandId=?",
                MappersHolder.ARTICLE_MAPPER, brandId);
    }

    @Override
    public List<Article> getAllOfficialArticlesByBrand(long brandId) {
        return jdbcTemplate.query("SELECT * FROM Article "
                + "INNER JOIN InformationSource ON InfoSourceId = InformationSource.Id WHERE BrandId=? "
                + " AND InformationSource.TypeId = 1", MappersHolder.ARTICLE_MAPPER, brandId);
    }

    @Override
    public List<Article> getAllArticles() {
        return jdbcTemplate.query("SELECT * FROM Article", MappersHolder.ARTICLE_MAPPER);
    }

    @Override
    public List<Article> getArticlesWithCondition(String whereClause) {
        return jdbcTemplate.query("SELECT * FROM Article " + whereClause, MappersHolder.ARTICLE_MAPPER);
    }

    @Override
    public List<Article> getTopArticles(long brandId, int topSize) {
        return jdbcTemplate.query("SELECT * FROM Article WHERE brandId=? "
                + " ORDER BY Tstamp DESC LIMIT ?", MappersHolder.ARTICLE_WITH_SHORT_CONTENT_MAPPER, brandId, topSize);
    }

    @Override
    public List<Article> getTopArticles(int topSize) {
        return jdbcTemplate.query("SELECT * FROM Article ORDER BY Tstamp DESC LIMIT ?"
                , MappersHolder.ARTICLE_WITH_SHORT_CONTENT_MAPPER, topSize);
    }

    @Override
    public List<Article> getAllArticlesByBrandAndSource(long brandId, long sourceId) {
        return jdbcTemplate.query("SELECT * FROM Article WHERE BrandId =? AND SourceId=?",
                MappersHolder.ARTICLE_MAPPER, brandId, sourceId);
    }
}
