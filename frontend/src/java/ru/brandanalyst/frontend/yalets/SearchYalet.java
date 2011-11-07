package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.xml.Xmler;
import ru.brandanalyst.frontend.services.BrandSearchManager;
import ru.brandanalyst.frontend.services.ArticleSearchManager;
import ru.brandanalyst.frontend.models.SimplyBrandForWeb;
import ru.brandanalyst.frontend.models.SimplyArticleForWeb;

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
            Xmler.Tag ans = Xmler.tag("answer", "Пустой запрос. Query: " + query);
            res.add(ans);
            return;
        }

        if (queryType != null) {
            List<SimplyArticleForWeb> articleIssuance = articleSearchManager.getSearchResultByArticle(query);
            if (articleIssuance != null) {
                if (articleIssuance.size() != 0) {
                    res.add(articleIssuance);
                } else {
                    Xmler.Tag ans = Xmler.tag("answer", "Ничего не найдено. Query by articles: " + query);
                    res.add(ans);
                }
            } else {
                Xmler.Tag ans = Xmler.tag("answer", "Убейтесь, все сломалось, ничего не работает или слишком сложный запрос. Query by articles: " + query);
                res.add(ans);
            }
        } else {
            List<SimplyBrandForWeb> brandIssuance = brandSearchManager.getSearchResultByBrand(query);

            if (brandIssuance != null) {
                if (brandIssuance.size() != 0) {
                    res.add(brandIssuance);
                } else {
                    Xmler.Tag ans = Xmler.tag("answer", "Ничего не найдено. Query by brands: " + query);
                    res.add(ans);
                }
            } else {
                Xmler.Tag ans = Xmler.tag("answer", "Убейтесь, все сломалось, ничего не работает или слишком сложный запрос. Query by brands: " + query);
                res.add(ans);
            }
        }
    }

}
