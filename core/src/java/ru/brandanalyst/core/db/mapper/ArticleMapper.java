package ru.brandanalyst.core.db.mapper;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.brandanalyst.core.model.Article;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 09.10.11
 * Time: 20:26
 * Mapping article from row of DB
 */
public class ArticleMapper implements ParameterizedRowMapper<Article> {
    public final Article mapRow(final ResultSet resultSet, final int i) throws SQLException {
        try {
            return new Article(Long.parseLong(resultSet.getString("Id")),
                Long.parseLong(resultSet.getString("BrandId")),
                Long.parseLong(resultSet.getString("InfosourceId")),
                resultSet.getString("Title"), resultSet.getString("Content"),
                resultSet.getString("Link"), resultSet.getTimestamp("Tstamp"),
                Integer.parseInt(resultSet.getString("NumLikes")));
        } catch (Exception e) {
            return new Article(Long.parseLong(resultSet.getString("Id")),
                Long.parseLong(resultSet.getString("BrandId")),
                Long.parseLong(resultSet.getString("InfosourceId")),
                resultSet.getString("Title"), resultSet.getString("Content"),
                resultSet.getString("Link"), resultSet.getTimestamp("Tstamp"), -1);
        }
    }
}
