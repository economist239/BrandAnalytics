package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Obus
 * Date: 14.02.12
 * Time: 14:07
 */
public class GetNewestArticlesYalet extends AbstractDbYalet {
    @Override
    public void process(InternalRequest req, InternalResponse res) {
        res.add(providersHandler.getArticleProvider().getTopArticles(OUTPUT_SIZE));
    }

    private static final int OUTPUT_SIZE = 5;
}
