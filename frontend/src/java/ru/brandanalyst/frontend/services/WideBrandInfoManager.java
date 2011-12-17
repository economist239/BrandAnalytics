package ru.brandanalyst.frontend.services;

import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.db.provider.interfaces.ArticleProvider;
import ru.brandanalyst.core.db.provider.interfaces.BrandProvider;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.model.*;
import ru.brandanalyst.core.model.simple.GraphForWeb;
import ru.brandanalyst.core.model.simple.SimplyArticleForWeb;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис, предоставляющий широкую информацию о бренде, включая последние новости о нем и анализ графиков
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/19/11
 * Time: 7:34 PM
 * gets wide information about brand from db
 */
public class WideBrandInfoManager extends AbstractManager {

    /**
     * количество показываемых последних новостей
     */
    private final static int NUM_ARTICLES = 6;

    public WideBrandInfoManager(ProvidersHandler providersHandler) {
        super(providersHandler);
    }

    public List<TickerPair> getTickers() {

        return providersHandler.getTickerProvider().getTickers();
    }

    public Brand getBrand(long brandId) {
        BrandProvider brandProvider = providersHandler.getBrandProvider();
        return brandProvider.getBrandById(brandId);
    }

    /**
     * В будущем предполагается виджет, со своим сервисом, который заменит этот метод
     */
    public GraphForWeb getGraphsForBrand(long brandId, long tickerId) {
        GraphProvider graphProvider = providersHandler.getGraphProvider();
        Graph graph = graphProvider.getGraphByTickerAndBrand(brandId, tickerId);
        GraphForWeb graphForWeb = new GraphForWeb(graph.getTicker());
        for (SingleDot d : graph.getGraph()) {
            graphForWeb.addDot(d);
        }
        return graphForWeb;
    }

    public List<SimplyArticleForWeb> getArticlesForBrand(long brandId) {
        ArticleProvider articleProvider = providersHandler.getArticleProvider();
        List<Article> articles = articleProvider.getTopArticles(brandId, NUM_ARTICLES);
        List<SimplyArticleForWeb> simplyArticles = new ArrayList<SimplyArticleForWeb>();
        //TODO getting source info by id
        for (Article a : articles) {
            simplyArticles.add(new SimplyArticleForWeb(a.getTitle(), a.getId(), firstPhrase(a.getContent()), Long.toString(a.getSourceId()), "ya.ru"));
        }
        return simplyArticles;
    }

    private String firstPhrase(String text) {
        int p = text.indexOf(". ");
        return text.substring(0, p) + "...";
    }
}
