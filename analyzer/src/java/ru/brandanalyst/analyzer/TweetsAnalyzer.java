package ru.brandanalyst.analyzer;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.ArticleProvider;
import ru.brandanalyst.core.db.provider.SemanticDictionaryProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.SemanticDictionaryItem;

import java.io.*;
import java.util.*;

/**
 * Класс анализирует содержимое твитов
 * Created by IntelliJ IDEA.
 * User: Alexander Senov
 * Date: 06.11.11
 * Time: 10:59
 */
public class TweetsAnalyzer {
    private static final Logger log = Logger.getLogger(Analyzer.class);
    /**шаблон первичной БД*/
    private SimpleJdbcTemplate dirtyJdbcTemplate;
        /**
    * метод устанавливает связь анализатора и первичной базы данных
    * @param dirtyJdbcTemplate шаблон перичной базы данных
    */
    public void setDirtyJdbcTemplate(SimpleJdbcTemplate dirtyJdbcTemplate) {
        this.dirtyJdbcTemplate = dirtyJdbcTemplate;
    }


    /** метод считает количество семантических термов в текстах статей (твитов)
    */
    public List<ArticleWordContainer> CountWordsInTweetsAsTriples(){
        log.info("starts tweets opinion analysis ...");
        if(dirtyJdbcTemplate != null){
            SemanticDictionaryProvider semanticDictionaryProvider = new SemanticDictionaryProvider(dirtyJdbcTemplate);

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
            ArticleProvider provider = new ArticleProvider(dirtyJdbcTemplate);
            int tweeterId = 1 ; //TODO: change 1 to real tweeter id
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
