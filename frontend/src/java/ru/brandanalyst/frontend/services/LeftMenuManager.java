package ru.brandanalyst.frontend.services;

import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.db.provider.interfaces.BrandProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.simple.SimplyBrandForWeb;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис, формирующий информацию из БД для левого меню
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/16/11
 * Time: 4:10 PM
 */
public class LeftMenuManager extends AbstractManager {

    public LeftMenuManager(ProvidersHandler providersHandler) {
        super(providersHandler);
    }

    public List<SimplyBrandForWeb> getSearchResultByBrand() {

        BrandProvider brandProvider = providersHandler.getBrandProvider();

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
