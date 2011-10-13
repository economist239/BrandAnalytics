package ru.brandanalyst.core.db.mapper;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.brandanalyst.core.model.Article;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: 1
 * Date: 09.10.11
 * Time: 20:26
 * To change this template use File | Settings | File Templates.
 */
public class ArticleMapper implements ParameterizedRowMapper<Article> {
        public Article mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Article(Integer.parseInt(resultSet.getString("article_id")), Integer.parseInt(resultSet.getString("info_source_id")),resultSet.getString("title"),resultSet.getString("content"),resultSet.getString("author"),resultSet.getString("timestmp"));
    }
}
