package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;
import net.sf.xfresh.db.AbstractDbYalet;
import ru.brandanalyst.frontend.models.WideArticleForWeb;
import ru.brandanalyst.frontend.services.ArticleManager;
import ru.brandanalyst.core.model.Article;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/25/11
 * Time: 11:40 PM
 */
public class ShowArticleYalet extends AbstractDbYalet {

    public void process(InternalRequest req, InternalResponse res) {

        long articleId = req.getLongParameter("id");

        ArticleManager manager = new ArticleManager(jdbcTemplate);
        WideArticleForWeb article = manager.getArticle(articleId);
        if (article != null) {
            res.add(article);
        } else {
            Xmler.Tag ans = Xmler.tag("error", "Трололо");
            res.add(ans);
        }
    }
}
