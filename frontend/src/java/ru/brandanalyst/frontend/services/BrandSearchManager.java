package ru.brandanalyst.frontend.services;

import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.frontend.models.SimplyBrandForWeb;
import ru.brandanalyst.searcher.Searcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис, производящий поиск брендов по индексу
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/6/11
 * Time: 5:30 PM
 */
public class BrandSearchManager {
    private Searcher searcher;

    public void setSearcher(Searcher searcher) {
        this.searcher = searcher;
    }

    public List<SimplyBrandForWeb> getSearchResultByBrand(String query) {

        try {
            List<SimplyBrandForWeb> brandList = new ArrayList<SimplyBrandForWeb>();
            for (Brand b : searcher.searchBrandByDescription(query)) {
                brandList.add(new SimplyBrandForWeb(b.getId(), b.getName(), b.getDescription(), b.getWebsite()));
            }
            return brandList;
        } catch (Exception e) {
            return null;
        }
    }

}
