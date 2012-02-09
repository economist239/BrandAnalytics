package ru.brandanalyst.analyzer.analyzer.quant;

import ru.brandanalyst.analyzer.util.VectorableUtil;
import ru.brandanalyst.core.db.provider.interfaces.ArticleProvider;
import ru.brandanalyst.core.db.provider.interfaces.BrandProvider;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.db.provider.interfaces.SemanticDictionaryProvider;
import ru.brandanalyst.core.model.*;
import ru.brandanalyst.core.time.TimeProperties;
import ru.brandanalyst.core.util.cortege.Triple;

import java.sql.Timestamp;
import java.util.*;

/**
 * Класс анализирует содержимое твитов
 * Created by IntelliJ IDEA.
 * User: Alexander Senov
 * Date: 06.11.11
 * Time: 10:59
 */
public class TweetsAnalyzer extends AbstractAnalyzer {
    private static final int TWEET_SOURCE_ID = 2;
    private static final int POSITIVE_TICKER_ID = 2;
    private static final int NEUTRAL_TICKER_ID = 3;
    private static final int NEGATIVE_TICKER_ID = 4;

    /**
     * метод записывает в базу данных графики упоминаемости брендов
     * в зависимости от эмоциональности
     */
    private ArticleProvider dirtyArticleProvider;
    private BrandProvider dirtyBrandProvider;
    private GraphProvider pureGraphProvider;
    private SemanticDictionaryProvider dictionaryProvider;

    public void analyze() {

        log.info("tweets analyzing started...");
        dirtyBrandProvider = dirtyProvidersHandler.getBrandProvider();
        dirtyArticleProvider = dirtyProvidersHandler.getArticleProvider();
        pureGraphProvider = dirtyProvidersHandler.getGraphProvider();
        dictionaryProvider = dirtyProvidersHandler.getSemanticDictionaryProvider();

        Map<Long, Triple<Double, Double, Double>> graphsMap = new HashMap<Long, Triple<Double, Double, Double>>();
        Map<Long, Double> graphMapPositive = new HashMap<Long, Double>();
        Map<Long, Double> graphMapNeutral = new HashMap<Long, Double>();
        Map<Long, Double> graphMapNegative = new HashMap<Long, Double>();

        //counting value
        Set<SemanticDictionaryItem> dictionary = dictionaryProvider.getSemanticDictionary();
        for (Brand b : dirtyBrandProvider.getAllBrands()) {

            List<Article> allArticles = dirtyArticleProvider.getAllArticlesByBrandAndSource(b.getId(), TWEET_SOURCE_ID);
            List<Article> articlesPositive = new ArrayList<Article>();
            List<Article> articlesNeutral = new ArrayList<Article>();
            List<Article> articlesNegative = new ArrayList<Article>();

            for (Article a : allArticles) {
                double value = getSentiment(a, dictionary);
                if (value > 0) {
                    articlesPositive.add(a);
                } else if (value < 0) {
                    articlesNegative.add(a);
                } else {
                    articlesNeutral.add(a);
                }
            }

            calculateGraphForBrand(b, articlesNegative, graphMapNegative);
            calculateGraphForBrand(b, articlesNeutral, graphMapNeutral);
            calculateGraphForBrand(b, articlesPositive, graphMapPositive);
            //map to graph
            putGraphToDB(pureGraphProvider, b, graphMapNegative, NEGATIVE_TICKER_ID);
            putGraphToDB(pureGraphProvider, b, graphMapNeutral, NEUTRAL_TICKER_ID);
            putGraphToDB(pureGraphProvider, b, graphMapPositive, POSITIVE_TICKER_ID);

        }
        log.info("graph analazing finished succesful");
    }

    protected double getSentiment(Article a, Set<SemanticDictionaryItem> dictionary) {
        double result = 0;
        for (SemanticDictionaryItem item : dictionary) {
            int count = VectorableUtil.countsSubInString(a.getContent(), item.getTerm());
            if (count > 0) {
                result += count * item.getSemanticValue();
            }
        }
        return result;
    }

    protected void calculateGraphForBrand(Brand b, List<Article> articles, Map<Long, Double> graphMap) {
        for (Article a : articles) {
            Timestamp timestamp = a.getTstamp();
            if (graphMap.containsKey(timestamp.getTime())) {
                graphMap.put(timestamp.getTime(), graphMap.get(timestamp.getTime()) + 1.0);
            } else {
                graphMap.put(timestamp.getTime(), 1.0);
            }
        }
    }

    protected void putGraphToDB(GraphProvider provider, Brand b, Map<Long, Double> graphMap, int tickerId) {
        Graph graph = new Graph("");
        for (long t = TimeProperties.TIME_LIMIT; t < new Date().getTime(); t += TimeProperties.SINGLE_DAY) {
            graph.addPoint(new SingleDot(new Timestamp(t), graphMap.get(t)));
        }
        provider.writeGraph(graph, b.getId(), tickerId);
    }


}
