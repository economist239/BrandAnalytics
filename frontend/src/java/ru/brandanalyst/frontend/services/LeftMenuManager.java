package ru.brandanalyst.frontend.services;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.mysql.MySQLBrandProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.frontend.models.SimplyBrandForWeb;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис, формирующий информацию из БД для левого меню
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/16/11
 * Time: 4:10 PM
 */
public class LeftMenuManager {

    private final SimpleJdbcTemplate jdbcTemplate;

    public LeftMenuManager(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SimplyBrandForWeb> getSearchResultByBrand() {

        MySQLBrandProvider brandProvider = new MySQLBrandProvider(jdbcTemplate);

        try {
            List<SimplyBrandForWeb> brandList = new ArrayList<SimplyBrandForWeb>();
            for (Brand b : brandProvider.getAllBrands()) {
                brandList.add(new SimplyBrandForWeb(b.getId(), b.getName(), b.getDescription(), b.getWebsite()));
            }
            return brandList;
        } catch (Exception e) {
            return null;
        }
    }
}
