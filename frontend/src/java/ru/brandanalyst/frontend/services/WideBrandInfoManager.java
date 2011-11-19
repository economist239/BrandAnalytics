package ru.brandanalyst.frontend.services;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.ArticleProvider;
import ru.brandanalyst.core.db.provider.BrandProvider;
import ru.brandanalyst.core.db.provider.GraphProvider;
import ru.brandanalyst.core.db.provider.TickerProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.model.SingleDot;
import ru.brandanalyst.frontend.models.GraphForWeb;
import ru.brandanalyst.frontend.models.SimplyArticleForWeb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Сервис, предоставляющий широкую информацию о бренде, включая последние новости о нем и анализ графиков
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/19/11
 * Time: 7:34 PM
 * gets wide information about brand from db
 */
public class WideBrandInfoManager {

    /**
     * количество показываемых последних новостей
     */
    private final static int NUM_ARTICLES = 6;
    private final SimpleJdbcTemplate jdbcTemplate;

    public WideBrandInfoManager(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TickerProvider.TickerPair> getTickers() {
        return new TickerProvider(jdbcTemplate).getTickers();
    }

    public Brand getBrand(long brandId) {
        BrandProvider brandProvider = new BrandProvider(jdbcTemplate);
        return brandProvider.getBrandById(brandId);
    }

    /**
     * В будущем предполагается виджет, со своим сервисом, который заменит этот метод
     */
    public GraphForWeb getGraphsForBrand(long brandId, long tickerId) {
        GraphProvider graphProvider = new GraphProvider(jdbcTemplate);
        Graph graph = graphProvider.getGraphByTickerAndBrand(brandId, tickerId);
        GraphForWeb graphForWeb = new GraphForWeb(graph.getTicker());
        for (SingleDot d : graph.getGraph()) {
                graphForWeb.addDot(d);
         }
        return graphForWeb;
    }

    public List<SimplyArticleForWeb> getArticlesForBrand(long brandId) {
        ArticleProvider articleProvider = new ArticleProvider(jdbcTemplate);
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
