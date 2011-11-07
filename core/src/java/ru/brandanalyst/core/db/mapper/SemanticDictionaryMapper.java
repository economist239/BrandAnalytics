package ru.brandanalyst.core.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.brandanalyst.core.model.SemanticDictionaryItem;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/4/11
 * Time: 9:24 AM
 */
public class SemanticDictionaryMapper implements ParameterizedRowMapper<SemanticDictionaryItem> {
    public final SemanticDictionaryItem mapRow(final ResultSet resultSet, final int i) throws SQLException {
        return new SemanticDictionaryItem(resultSet.getString("Term"), Double.parseDouble(resultSet.getString("SemanticValue")));
    }
}
