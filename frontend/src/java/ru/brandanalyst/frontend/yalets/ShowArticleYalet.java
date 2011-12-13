package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;
import ru.brandanalyst.core.model.simple.WideArticleForWeb;
import ru.brandanalyst.frontend.services.ArticleManager;

/**
 * Ялет, показывающий полный тест новости
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/25/11
 * Time: 11:40 PM
 */
public class ShowArticleYalet extends AbstractDbYalet {

    public void process(InternalRequest req, InternalResponse res) {

        long articleId = req.getLongParameter("id");

        ArticleManager manager = new ArticleManager(providersHandler);
        WideArticleForWeb article = manager.getArticle(articleId);
        if (article != null) {
            res.add(article);
        } else {
            Xmler.Tag ans = Xmler.tag("error", "Трололо");
            res.add(ans);
        }
    }
}
