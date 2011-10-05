package net.sf.xfresh.db;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
* Date: Nov 9, 2010
* Time: 1:45:29 PM
*
* @author Nikolay Malevanny nmalevanny@yandex-team.ru
*/
class RecordMapper implements ParameterizedRowMapper<Record> {
    public Record mapRow(final ResultSet resultSet, final int rowNumber) throws SQLException {
        final Record record = new Record();
        final ResultSetMetaData metaData = resultSet.getMetaData();
        final int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            record.addCell(
                    metaData.getColumnName(i),
                    resultSet.getObject(i)
            );
        }
        return record;
    }
}
