package ru.brandanalyst.analyzer.util;

import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.SemanticDictionaryItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Senov
 * Date: 11.12.11
 * Time: 17:05
 */
// мало ли что захотим векторизовать
public final class VectorableUtil {

    private VectorableUtil() {
    }

    public static int countsSubInString(String target, String sub) {
        int count = 0;
        int indexFrom = 0;
        while ((indexFrom = target.indexOf(sub, indexFrom)) > 0) {
            ++count;
            indexFrom += sub.length();
        }
        return count;
    }

    public static List<Double> asVector(Article a, Set<SemanticDictionaryItem> dictionary) {
        List<Double> result = new ArrayList<Double>();

        for (SemanticDictionaryItem item : dictionary) {
            result.add((double) countsSubInString(a.getContent(), item.getTerm()));
        }

        return result;
    }


    /** метод считает количество семантических термов в текстах статей (твитов)
     */
    /*
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
                count = VectorableUtil.countsSubInString(article.getContent(), dictionaryItem.getTerm());
                if(count > 0){
                    articleWordCount.add(new ArticleWordContainer(article.getId(),dictionaryItem,count));
                }
            }
        }

        return articleWordCount;
    }
    else{
        //log.error("variable dirtyJdbcTemplate have not been initialized");
        return null;
    }
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
    */
}
