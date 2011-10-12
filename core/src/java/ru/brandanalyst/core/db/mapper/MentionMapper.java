package ru.brandanalyst.core.db.mapper;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.brandanalyst.core.model.Mention;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: 1
 * Date: 09.10.11
 * Time: 21:40
 * To change this template use File | Settings | File Templates.
 */
public class MentionMapper implements ParameterizedRowMapper<Mention> {
        public Mention mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Mention(Integer.parseInt(resultSet.getString("brand_id")), Long.parseLong(resultSet.getString("article_id")),Double.parseDouble(resultSet.getString("mention")));
    }
}
