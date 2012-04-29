package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.xml.Xmler;
import org.springframework.beans.factory.annotation.Required;
import ru.brandanalyst.core.searcher.Searcher;

/**
 * Ялет отображения поисковой выдачи
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 04.10.11
 * Time: 3:45
 */
public class SearchYalet implements Yalet {

    private Searcher searcher;

    @Required
    public void setSearcher(Searcher searcher) {
        this.searcher = searcher;
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
            res.add(searcher.searchBrandByDescription(query));
        } else {
            res.add(searcher.searchArticleByContent(query));
        }
    }

}
