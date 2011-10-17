package ru.brandanalyst.frontend.services;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.BrandProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.frontend.models.SimplyBrandForWeb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 10/16/11
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class LeftMenuManager {

    public List<SimplyBrandForWeb> getSearchResultByBrand(SimpleJdbcTemplate jdbcTemplate) {

        BrandProvider brandProvider = new BrandProvider(jdbcTemplate);

        try{
            List<SimplyBrandForWeb> brandList = new ArrayList<SimplyBrandForWeb>();
            for (Brand b: brandProvider.getAllBrands()) {
                brandList.add(new SimplyBrandForWeb(b.getName(),b.getDescription(),b.getWebsite()));
            }
            return brandList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
