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
import ru.brandanalyst.core.db.mapper.BrandMapper;
import ru.brandanalyst.core.db.provider.global.BrandProvider;
import ru.brandanalyst.core.model.Brand;

import java.util.List;

public class MySQLBrandProvider implements BrandProvider {
    private static final Logger log = Logger.getLogger(MySQLBrandProvider.class);

    private SimpleJdbcTemplate jdbcTemplate; //
    private BrandMapper brandMapper;

    public MySQLBrandProvider(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        brandMapper = new BrandMapper();
    }

    @Deprecated
    public void cleanDataStore() {
        jdbcTemplate.update("TRUNCATE TABLE Brand");
    }

    public void writeBrandToDataStore(Brand brand) {
        try {
            jdbcTemplate.update("INSERT INTO Brand (Name, Description, Website, BranchId, FinamName) VALUES(?,?,?,?,?);", brand.getName(),
                    brand.getDescription(), brand.getWebsite(), brand.getBranchId(), brand.getFinamName());
        } catch (Exception e) {
            log.error("cannot wrtie brand to db");
        }
    }

    public void writeListOfBrandsToDataStore(List<Brand> brands) {
        for (Brand b : brands) {
            writeBrandToDataStore(b);
        }
    }

    public Brand getBrandByName(String name) {
        List<Brand> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Brand WHERE Name = " + name, brandMapper);
        return list.get(0);
    }

    public Brand getBrandById(long brandId) {
        List<Brand> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Brand WHERE Id = " + brandId, brandMapper);
        return list.get(0);
    }

    public List<Brand> getAllBrands() {
        List<Brand> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Brand", brandMapper);
        return list;
    }
}
