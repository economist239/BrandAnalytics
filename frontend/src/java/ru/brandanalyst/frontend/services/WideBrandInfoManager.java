package ru.brandanalyst.frontend.services;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.mysql.MySQLArticleProvider;
import ru.brandanalyst.core.db.provider.mysql.MySQLBrandProvider;
import ru.brandanalyst.core.db.provider.mysql.MySQLGraphProvider;
import ru.brandanalyst.core.db.provider.mysql.MySQLTickerProvider;
import ru.brandanalyst.core.model.*;
import ru.brandanalyst.frontend.models.GraphForWeb;
import ru.brandanalyst.frontend.models.SimplyArticleForWeb;

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
public class WideBrandInfoManager {

    /**
     * количество показываемых последних новостей
     */
    private final static int NUM_ARTICLES = 6;
    private final SimpleJdbcTemplate jdbcTemplate;

    public WideBrandInfoManager(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TickerPair> getTickers() {
        return new MySQLTickerProvider(jdbcTemplate).getTickers();
    }

    public Brand getBrand(long brandId) {
        MySQLBrandProvider brandProvider = new MySQLBrandProvider(jdbcTemplate);
        return brandProvider.getBrandById(brandId);
    }

    /**
     * В будущем предполагается виджет, со своим сервисом, который заменит этот метод
     */
    public GraphForWeb getGraphsForBrand(long brandId, long tickerId) {
        MySQLGraphProvider graphProvider = new MySQLGraphProvider(jdbcTemplate);
        Graph graph = graphProvider.getGraphByTickerAndBrand(brandId, tickerId);
        GraphForWeb graphForWeb = new GraphForWeb(graph.getTicker());
        for (SingleDot d : graph.getGraph()) {
                graphForWeb.addDot(d);
         }
        return graphForWeb;
    }

    public List<SimplyArticleForWeb> getArticlesForBrand(long brandId) {
        MySQLArticleProvider articleProvider = new MySQLArticleProvider(jdbcTemplate);
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
