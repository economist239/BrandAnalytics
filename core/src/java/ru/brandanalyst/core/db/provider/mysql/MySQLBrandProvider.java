package ru.brandanalyst.core.db.provider.mysql;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 09.10.11
 * Time: 17:14
 * this class provides brands from DB
 */

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.interfaces.BrandProvider;
import ru.brandanalyst.core.model.Brand;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class MySQLBrandProvider implements BrandProvider {
    private SimpleJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void writeBrandToDataStore(Brand brand) {
        jdbcTemplate.update("INSERT INTO Brand (Name, Description, Website, BranchId, FinamName) VALUES(?,?,?,?,?)", brand.getName(),
                brand.getDescription(), brand.getWebsite(), brand.getBranchId(), brand.getFinamName());
    }

    @Override
    public void writeListOfBrandsToDataStore(final List<Brand> brands) {
        final Iterator<Brand> it = brands.iterator();
        jdbcTemplate.getJdbcOperations().batchUpdate("INSERT INTO Brand (Name, Description, Website, BranchId, FinamName) VALUES(?,?,?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Brand b = it.next();
                ps.setString(1, b.getName());
                ps.setString(2, b.getDescription());
                ps.setString(3, b.getWebsite());
                ps.setLong(4, b.getBranchId());
                ps.setString(5, b.getFinamName());
            }

            @Override
            public int getBatchSize() {
                return brands.size();
            }
        });
    }

    @Override
    public Brand getBrandByName(String name) {
        return jdbcTemplate.query("SELECT * FROM Brand WHERE Name =?", MappersHolder.BRAND_MAPPER, name).get(0);
    }

    @Override
    public Brand getBrandById(long brandId) {
        return jdbcTemplate.query("SELECT * FROM Brand WHERE Id=?", MappersHolder.BRAND_MAPPER, brandId).get(0);
    }

    @Override
    public List<Brand> getAllBrands() {
        return jdbcTemplate.query("SELECT * FROM Brand", MappersHolder.BRAND_MAPPER);
    }
}
