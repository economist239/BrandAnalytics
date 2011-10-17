package ru.brandanalyst.frontend.services;

import java.util.List;
import java.util.ArrayList;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.searcher.Searcher;
import ru.brandanalyst.frontend.models.SimplyBrandForWeb;

public class SearchManager {
    private Searcher searcher;

    public void setSearcher(Searcher searcher) {
        this.searcher = searcher;
    }

    public List<SimplyBrandForWeb> getSearchResultByBrand(String query) {

        try{
            List<SimplyBrandForWeb> brandList = new ArrayList<SimplyBrandForWeb>();
            for (Brand b: searcher.searchByDescription(query)) {
                brandList.add(new SimplyBrandForWeb(b.getName(),b.getDescription(),b.getWebsite()));
            }
            return brandList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
