package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

/**
 * Ялет, показывающий полный тест новости
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/25/11
 * Time: 11:40 PM
 */
public class GetArticleForWebYalet extends AbstractDbYalet {

    public void process(InternalRequest req, InternalResponse res) {
        long articleId = req.getLongParameter("id");
        res.add(providersHandler.getArticleProvider().getArticleForWebById(articleId));
    }
}
