package ru.brandanalyst.storage.provider;

/**
 * Created by IntelliJ IDEA.
 * User: 1
 * Date: 09.10.11
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.model.*;
import ru.brandanalyst.storage.mapper.BrandMapper;

import java.util.List;

public class BrandProvider {
    private SimpleJdbcTemplate jdbcTemplate; //
    private BrandMapper brandMapper;

    public BrandProvider(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        brandMapper = new BrandMapper();
    }

    public void cleanDataStore() {
        jdbcTemplate.update("TRUNCATE TABLE Brand");
    }

    public void writeBrandToDataStore(Brand brand) {
        try{
            jdbcTemplate.update("INSERT INTO Brand (brand_id, name, description, website, branch) VALUES(?, ?, ?,'ddd','ddd');", brand.getId(), brand.getName(),
                brand.getDescription());
        } catch (Exception e) {}
    }

    public void writeListOfBrandsToDataStore(List<Brand> brands) {
        for (Brand b : brands) {
            writeBrandToDataStore(b);
        }
    }

    public Brand getBrandByName(String name) {
        List<Brand> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Brand WHERE name = ?", new Object[]{name}, brandMapper);
        return list.get(0);
    }

    public Brand getBrandById(int brand_id) {
        List<Brand> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Brand WHERE brand_id = " + Integer.toString(brand_id) , brandMapper);
        return list.get(0);
    }
    public List<Brand> getAllBrands() {
        List<Brand> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Brand", brandMapper);
        return list;
    }
}
