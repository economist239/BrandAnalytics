package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/11/12
 * Time: 10:30 AM
 */
public class GetArticlesForBrandYalet extends AbstractDbYalet {
    private final static int NUM_ARTICLES = 6;

    @Override
    public void process(InternalRequest req, InternalResponse res) {
        long brandId = req.getLongParameter("id");
        res.add(providersHandler.getArticleProvider().getTopArticles(brandId, NUM_ARTICLES));
    }
}
