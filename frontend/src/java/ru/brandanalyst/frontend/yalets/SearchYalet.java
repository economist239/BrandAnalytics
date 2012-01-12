package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.xml.Xmler;
import ru.brandanalyst.core.model.simple.SimplyArticleForWeb;
import ru.brandanalyst.core.model.simple.SimplyBrandForWeb;
import ru.brandanalyst.frontend.services.ArticleSearchManager;
import ru.brandanalyst.frontend.services.BrandSearchManager;

import java.util.List;

/**
 * Ялет отображения поисковой выдачи
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 04.10.11
 * Time: 3:45
 */
public class SearchYalet implements Yalet {

    private BrandSearchManager brandSearchManager;
    private ArticleSearchManager articleSearchManager;

    public void setBrandSearchManager(BrandSearchManager brandSearchManager) {
        this.brandSearchManager = brandSearchManager;
    }

    public void setArticleSearchManager(ArticleSearchManager articleSearchManager) {
        this.articleSearchManager = articleSearchManager;
    }

    public void process(InternalRequest req, InternalResponse res) {
        String query = req.getParameter("query");
        String queryType = req.getParameter("query_type");
        if (query.isEmpty()) {
            Xmler.Tag ans = Xmler.tag("answer", "Пустой запрос");
            res.add(ans);
            return;
        }

        if (queryType != null) {
            List<SimplyArticleForWeb> articleIssuance = articleSearchManager.getSearchResultByArticle(query);
            if (articleIssuance.size() != 0) {
                res.add(articleIssuance);
                return;
            }
        } else {
            List<SimplyBrandForWeb> brandIssuance = brandSearchManager.getSearchResultByBrand(query);
            if (brandIssuance.size() != 0) {
                res.add(brandIssuance);
                return;
            }
        }
        res.add(Xmler.tag("answer", "error, try to change your query"));
    }

}
