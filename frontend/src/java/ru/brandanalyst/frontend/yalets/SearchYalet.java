package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.xml.Xmler;
import ru.brandanalyst.frontend.services.SearchManager;
import ru.brandanalyst.frontend.models.SimplyBrandForWeb;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 04.10.11
 * Time: 3:45
 */
public class SearchYalet implements Yalet {

    private SearchManager searchManager;

    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    public void process(InternalRequest req, InternalResponse res) {
        String query = req.getParameter("query");

        if (query.isEmpty()) {
            Xmler.Tag ans = Xmler.tag("answer", "Пустой запрос. Query: " + query);
            res.add(ans);
            return;
        }

        List<SimplyBrandForWeb> brandIssuance = searchManager.getSearchResultByBrand(query);

        if (brandIssuance != null) {
            if (brandIssuance.size() != 0) {
                res.add(brandIssuance);
            } else {
                Xmler.Tag ans = Xmler.tag("answer", "Ничего не найдено. Query: " + query);
                res.add(ans);
            }
        } else {
            Xmler.Tag ans = Xmler.tag("answer", "Убейтесь, все сломалось, ничего не работает или слишком сложный запрос. Query: " + query);
            res.add(ans);
        }
    }

}
