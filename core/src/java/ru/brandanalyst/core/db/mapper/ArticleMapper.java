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
        try{
            return new Article(Long.parseLong(resultSet.getString("Id")), Long.parseLong(resultSet.getString("InfosourceId")),resultSet.getString("Title"),resultSet.getString("Content"),resultSet.getString("Link"),resultSet.getString("Tstamp"),Integer.parseInt(resultSet.getString("NumLikes")));
        } catch (Exception e) {
            return new Article(Long.parseLong(resultSet.getString("Id")), Long.parseLong(resultSet.getString("InfosourceId")),resultSet.getString("Title"),resultSet.getString("Content"),resultSet.getString("Link"),resultSet.getString("Tstamp"),-1);
        }
    }
}
