package ru.brandanalyst.frontend.services;

import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.db.provider.interfaces.ArticleProvider;
import ru.brandanalyst.core.db.provider.interfaces.InformationSourceProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.InfoSource;
import ru.brandanalyst.core.model.simple.WideArticleForWeb;

/**
 * Сервис, извлекающий новости по их идентификатору из БД
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/25/11
 * Time: 11:41 PM
 * article service (get article from db and push it to yalet)
 */
public class ArticleManager extends AbstractManager {

    public ArticleManager(ProvidersHandler providersHandler) {
        super(providersHandler);
    }

    public WideArticleForWeb getArticle(long id) {
        ArticleProvider articleProvider = providersHandler.getArticleProvider();
        Article article = articleProvider.getArticleById(id);

        InformationSourceProvider informationSourceProvider = providersHandler.getInformationSourceProvider();
        InfoSource infoSource = informationSourceProvider.getInfoSourceById(article.getSourceId());

        return new WideArticleForWeb(article.getLink(),
                article.getTitle(),
                article.getContent(),
                infoSource.getTitle(),
                infoSource.getWebsite(),
                article.getTstamp().toString());
    }
}
