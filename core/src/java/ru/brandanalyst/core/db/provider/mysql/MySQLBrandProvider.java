package ru.brandanalyst.core.db.provider.mysql;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 09.10.11
 * Time: 17:14
 * this class provides brands from DB
 */

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.interfaces.BrandProvider;
import ru.brandanalyst.core.model.Brand;

import java.util.List;

public class MySQLBrandProvider implements BrandProvider {
    private static final Logger log = Logger.getLogger(MySQLBrandProvider.class);

    private SimpleJdbcTemplate jdbcTemplate; //

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Deprecated
    public void cleanDataStore() {
        jdbcTemplate.update("TRUNCATE TABLE Brand");
    }

    @Override
    public void writeBrandToDataStore(Brand brand) {
        try {
            jdbcTemplate.update("INSERT INTO Brand (Name, Description, Website, BranchId, FinamName) VALUES(?,?,?,?,?);", brand.getName(),
                    brand.getDescription(), brand.getWebsite(), brand.getBranchId(), brand.getFinamName());
        } catch (Exception e) {
            log.error("cannot wrtie brand to db");
        }
    }

    @Override
    public void writeListOfBrandsToDataStore(List<Brand> brands) {
        for (Brand b : brands) {
            writeBrandToDataStore(b);
        }
    }

    @Override
    public Brand getBrandByName(String name) {
        List<Brand> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Brand WHERE Name = " +
                name, MappersHolder.BRAND_MAPPER);
        return list.get(0);
    }

    @Override
    public Brand getBrandById(long brandId) {
        List<Brand> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Brand WHERE Id = " +
                brandId, MappersHolder.BRAND_MAPPER);
        return list.get(0);
    }

    @Override
    public List<Brand> getAllBrands() {
        List<Brand> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Brand",
                MappersHolder.BRAND_MAPPER);
        return list;
    }
}
