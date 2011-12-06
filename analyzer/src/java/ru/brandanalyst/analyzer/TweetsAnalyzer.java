package ru.brandanalyst.analyzer;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.mysql.MySQLArticleProvider;
import ru.brandanalyst.core.db.provider.mysql.MySQLBrandProvider;
import ru.brandanalyst.core.db.provider.mysql.MySQLGraphProvider;
import ru.brandanalyst.core.db.provider.mysql.MySQLSemanticDictionaryProvider;
import ru.brandanalyst.core.model.*;
import ru.brandanalyst.core.time.TimeProperties;

import java.sql.Timestamp;
import java.util.*;

/**
 * Класс анализирует содержимое твитов
 * Created by IntelliJ IDEA.
 * User: Alexander Senov
 * Date: 06.11.11
 * Time: 10:59
 */
public class TweetsAnalyzer{
    private static final int TWEET_SOURCE_ID = 2;
    private static final int POSITIVE_TICKER_ID = 2;
    private static final int NEUTRAL_TICKER_ID = 3;
    private static final int NEGATIVE_TICKER_ID = 4;
    private static final Logger log = Logger.getLogger(Analyzer.class);

    /**
     * шаблон первичной БД
     */
    private SimpleJdbcTemplate dirtyJdbcTemplate;
    /**
     * шаблон вторичной БД
     */
    private SimpleJdbcTemplate pureJdbcTemplate;

    /**
     * @param pureJdbcTemplate  шаблон вторичной базы данных
     * @param dirtyJdbcTemplate шаблон первичной базы данных
     */
    public TweetsAnalyzer(SimpleJdbcTemplate pureJdbcTemplate, SimpleJdbcTemplate dirtyJdbcTemplate) {
        this.dirtyJdbcTemplate = dirtyJdbcTemplate;
        this.pureJdbcTemplate = pureJdbcTemplate;
    }

    /**
     * метод записывает в базу данных графики упоминаемости брендов
     * в зависимости от эмоциональности
     */

    public void analyze(){

        log.info("graph analyzing started...");
        MySQLBrandProvider dirtyBrandProvider = new MySQLBrandProvider(dirtyJdbcTemplate);
        MySQLBrandProvider pureBrandProvider = new MySQLBrandProvider(pureJdbcTemplate);
        MySQLArticleProvider dirtyArticleProvider = new MySQLArticleProvider(dirtyJdbcTemplate);
        MySQLArticleProvider pureArticleProvider = new MySQLArticleProvider(pureJdbcTemplate);
        MySQLGraphProvider pureGraphProvider = new MySQLGraphProvider(pureJdbcTemplate);
        MySQLSemanticDictionaryProvider dictionaryProvider = new MySQLSemanticDictionaryProvider(dirtyJdbcTemplate);


        Map<Long, Double> graphMapPositive = new HashMap<Long, Double>();
        Map<Long, Double> graphMapNeutral = new HashMap<Long, Double>();
        Map<Long, Double> graphMapNegative = new HashMap<Long, Double>();

        //counting value
        Set<SemanticDictionaryItem> dictionary = dictionaryProvider.getSemanticDictionary();
        for (Brand b : dirtyBrandProvider.getAllBrands()) {
            List<Article> allArticles = dirtyArticleProvider.getAllArticlesByBrandAndSource(b.getId(),TWEET_SOURCE_ID);
            List<Article> articlesPositive = new ArrayList<Article>();
            List<Article> articlesNeutral = new ArrayList<Article>();
            List<Article> articlesNegative = new ArrayList<Article>();

            for(Article a:allArticles){
                double value = getSentiment(a,dictionary);
                if(value > 0){
                    articlesPositive.add(a);
                }
                else if(value < 0){
                    articlesNegative.add(a);
                }
                else{
                    articlesNeutral.add(a);
                }
            }
            for (long t = TimeProperties.TIME_LIMIT; t < new Date().getTime(); t += TimeProperties.SINGLE_DAY) {
                graphMapNegative.put(t, 0.0);
                graphMapNeutral.put(t, 0.0);
                graphMapPositive.put(t, 0.0);
            }

            calculateGraphForBrand(b,articlesNegative,graphMapNegative);
            calculateGraphForBrand(b,articlesNeutral,graphMapNeutral);
            calculateGraphForBrand(b,articlesPositive,graphMapPositive);
            //map to graph
            putGraphToDB(pureGraphProvider,b,graphMapNegative,NEGATIVE_TICKER_ID);
            putGraphToDB(pureGraphProvider,b,graphMapNeutral,NEUTRAL_TICKER_ID);
            putGraphToDB(pureGraphProvider,b,graphMapPositive,POSITIVE_TICKER_ID);

        }
        log.info("graph analazing finished succesful");
    }

    protected double getSentiment(Article a, Set<SemanticDictionaryItem> dictionary){
        double result=0;
        for(SemanticDictionaryItem item:dictionary){
            int count = countsSubInString(a.getContent(),item.getTerm());
            if(count > 0){
                result += count*item.getSemanticValue();
            }
        }
        return result;
    }
    protected void calculateGraphForBrand(Brand b, List<Article> articles, Map<Long, Double> graphMap){
        for (Article a : articles) {
            Timestamp timestamp = a.getTstamp();
            if (graphMap.containsKey(timestamp.getTime())) {
                graphMap.put(timestamp.getTime(), graphMap.get(timestamp.getTime()) + 1.0);
            }
        }
    }
    protected void putGraphToDB(MySQLGraphProvider provider, Brand b, Map<Long, Double> graphMap, int tickerId ){
        Graph graph = new Graph("");
            for (long t = TimeProperties.TIME_LIMIT; t < new Date().getTime(); t += TimeProperties.SINGLE_DAY) {
                graph.addPoint(new SingleDot(new Timestamp(t), graphMap.get(t)));
            }
        provider.writeGraph(graph, b.getId(), tickerId);
    }

    /** метод считает количество семантических термов в текстах статей (твитов)
    */
    public List<ArticleWordContainer> CountWordsInTweetsAsTriples(){
        log.info("starts tweets opinion analysis ...");
        if(dirtyJdbcTemplate != null){
            MySQLSemanticDictionaryProvider semanticDictionaryProvider = new MySQLSemanticDictionaryProvider(dirtyJdbcTemplate);

            Set<SemanticDictionaryItem> dictionary = semanticDictionaryProvider.getSemanticDictionary();
            Iterator<SemanticDictionaryItem> dictionaryItemIterator = dictionary.iterator();
            List<Article> articles = getTweetsFromDB();
            Iterator<Article> articlesIterator =  articles.listIterator();

            List<ArticleWordContainer> articleWordCount = new ArrayList<ArticleWordContainer>();
            SemanticDictionaryItem dictionaryItem;
            Article article;
            int count;
            while(articlesIterator.hasNext()){
                article = articlesIterator.next();
                while(dictionaryItemIterator.hasNext()){
                    dictionaryItem = dictionaryItemIterator.next();
                    count = countsSubInString(article.getContent(),dictionaryItem.getTerm());
                    if(count > 0){
                        articleWordCount.add(new ArticleWordContainer(article.getId(),dictionaryItem,count));
                    }
                }
            }
            log.info("finish counting semantic word in articles");
            return articleWordCount;
        }
        else{
            log.error("variable dirtyJdbcTemplate have not been initialized");
            return null;
        }
    }
    public int countsSubInString(String target,String sub){
           int count = 0;
           int indexFrom = 0;
           while( (indexFrom = target.indexOf(sub,indexFrom)) > 0){
               ++count;
               indexFrom += sub.length();
           }
           return count;
       }

    public List<Article> getTweetsFromDB(){
        if(dirtyJdbcTemplate != null){
            MySQLArticleProvider provider = new MySQLArticleProvider(dirtyJdbcTemplate);
            int tweeterId = TWEET_SOURCE_ID ; //TODO: change 1 to real tweeter id
            return provider.getAllArticlesBySourceId(tweeterId);
        }
        return null;
    }

    public class ArticleWordContainer{
        private long articleId;
        private SemanticDictionaryItem dictionaryItem;// TODO: по-хорошему, для экономии, надо бы иметь id слова
        private int count;

        public ArticleWordContainer(long articleId, SemanticDictionaryItem dictionaryItem, int count) {
            this.articleId = articleId;
            this.count = count;
            this.dictionaryItem = dictionaryItem;
        }

        public long getArticleId() {
            return articleId;
        }

        public void setArticleId(long articleId) {
            this.articleId = articleId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
