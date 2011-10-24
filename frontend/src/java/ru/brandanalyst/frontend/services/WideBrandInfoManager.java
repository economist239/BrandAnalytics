package ru.brandanalyst.frontend.services;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.model.*;
import ru.brandanalyst.core.db.provider.*;
import ru.brandanalyst.frontend.models.GraphForWeb;
import ru.brandanalyst.frontend.models.SimplyArticleForWeb;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 10/19/11
 * Time: 7:34 PM
 */
public class WideBrandInfoManager {

    private final SimpleJdbcTemplate jdbcTemplate;

    public WideBrandInfoManager(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Brand getBrand(long brandId) {
        BrandProvider brandProvider = new BrandProvider(jdbcTemplate);
        return brandProvider.getBrandById(brandId);
    }

    public List<GraphForWeb> getGraphsForBrand(long brandId) {
        GraphProvider graphProvider = new GraphProvider(jdbcTemplate);
        List<Graph> graphList = graphProvider.getGraphsByBrandId(brandId);
        List<GraphForWeb> graphsList = new ArrayList<GraphForWeb>();
        return graphsList;
    }

    public List<SimplyArticleForWeb> getArticlesForBrand(long brandId) {
        List<SimplyArticleForWeb> articlesList = new ArrayList<SimplyArticleForWeb>();
        ArticleProvider articleProvider = new ArticleProvider(jdbcTemplate);
        List<Article> articles = articleProvider.getAllArticlesByBrand(brandId);
        List<SimplyArticleForWeb> simplyArticles = new ArrayList<SimplyArticleForWeb>();
        //TODO getting source info by id
        for(Article a: articles) {
            simplyArticles.add(new SimplyArticleForWeb(a.getTitle(),a.getId(),firstPhrase(a.getContent()),Long.toString(a.getSourceId()),"ya.ru"));
        }
        return simplyArticles;
    }

    private String firstPhrase(String text) {
        int point = text.indexOf('.');
        return text.substring(0,point);
    }
}
