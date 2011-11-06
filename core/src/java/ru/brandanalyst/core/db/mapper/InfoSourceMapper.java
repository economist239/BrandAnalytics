package ru.brandanalyst.core.db.mapper;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.brandanalyst.core.model.InfoSource;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/6/11
 * Time: 12:34 PM
 */
public class InfoSourceMapper implements ParameterizedRowMapper<InfoSource> {
    public final InfoSource mapRow(final ResultSet resultSet, final int i) throws SQLException {
        return new InfoSource(Long.parseLong(resultSet.getString("Id")),
                Long.parseLong(resultSet.getString("TypeId")),
                resultSet.getString("Title"),
                resultSet.getString("Description"),
                resultSet.getString("Website"));
    }
}
