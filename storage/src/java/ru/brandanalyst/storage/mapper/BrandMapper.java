package ru.brandanalyst.storage.mapper;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.brandanalyst.core.model.Brand;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: 1
 * Date: 09.10.11
 * Time: 17:17
 * To change this template use File | Settings | File Templates.
 */
public class BrandMapper implements ParameterizedRowMapper<Brand> {
    public Brand mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Brand(Integer.parseInt(resultSet.getString("brand_id")), resultSet.getString("name"),resultSet.getString("description"),resultSet.getString("website"),resultSet.getString("branch"));
    }
}
