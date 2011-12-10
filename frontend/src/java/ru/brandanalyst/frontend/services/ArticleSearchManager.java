package ru.brandanalyst.frontend.services;

import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.simple.SimplyArticleForWeb;
import ru.brandanalyst.core.util.TextConverter;
import ru.brandanalyst.searcher.Searcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис, производящий поиск новостей по индексу
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/6/11
 * Time: 5:30 PM
 */
public class ArticleSearchManager {
    private Searcher searcher;

    public void setSearcher(Searcher searcher) {
        this.searcher = searcher;
    }

    public List<SimplyArticleForWeb> getSearchResultByArticle(String query) {
        try {
            List<SimplyArticleForWeb> brandList = new ArrayList<SimplyArticleForWeb>();
            for (Article a : searcher.searchArticleByContent(query)) {
                brandList.add(new SimplyArticleForWeb(a.getTitle(), a.getId(), TextConverter.firstPhrase(a.getContent()), "", a.getLink()));
            }
            return brandList;
        } catch (Exception e) {
            return null;
        }
    }
}
