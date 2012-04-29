package ru.brandanalyst.db;

import org.joda.time.LocalDateTime;
import ru.brandanalyst.core.db.provider.EntityVisitor;
import ru.brandanalyst.core.db.provider.interfaces.ArticleProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.ArticleForWeb;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * User: dima
 * Date: 2/26/12
 * Time: 5:53 PM
 */
public class InMemoryArticleProvider extends ArticleProvider {
    private int key;
    private final List<Article> depot = new ArrayList<Article>();
    
    {
        depot.add(new Article(1,1,1,"11","11","http:/11",new LocalDateTime(1), 1));
        depot.add(new Article(2,2,2,"22","22","http:/22",new LocalDateTime(2), 1));
        depot.add(new Article(3,1,2,"12","12","http:/13",new LocalDateTime(3), 1));
        depot.add(new Article(4,2,2,"22","22","http:/22",new LocalDateTime(4), 1));
        depot.add(new Article(5,1,1,"11","11","http:/11",new LocalDateTime(5), 1));
        key = 5;
    }
    
    @Override
    public void writeArticleToDataStore(Article article) {
        depot.add(new Article(++key, article.getBrandId(), article.getSourceId(), article.getTitle(), 
                article.getContent(), article.getLink(), article.getTstamp(), article.getNumLikes()));
    }

    @Override
    public void writeListOfArticlesToDataStore(List<Article> articles) {
        for (Article a: articles) {
            writeArticleToDataStore(a);
        }
    }

    @Override
    public List<Article> getAllArticlesBySourceId(long sourceId) {
        final List<Article> as = new ArrayList<Article>();
        for (Article a: depot) {
            if (a.getSourceId() == sourceId) {
                as.add(a);
            }
        }
        return as;
    }

    @Override
    public Article getArticleById(long articleId) {
        return depot.get((int) articleId);
    }

    @Override
    public ArticleForWeb getArticleForWebById(long articleId) {
        Article a = depot.get((int) articleId);
        return new ArticleForWeb(a.getLink(), a.getTitle(), a.getContent(), "asd", "asd", a.getTstamp());       
    }

    @Override
    public List<Article> getAllArticlesByBrand(long brandId) {
        final List<Article> as = new ArrayList<Article>();
        for (Article a: depot) {
            if (a.getBrandId() == brandId) {
                as.add(a);
            }
        }
        return as;
    }

    @Override
    public List<Article> getAllArticles() {
        return new ArrayList<Article>(depot);
    }

    @Override
    public void visitArticles(EntityVisitor<Article> visitor) {
        for(Article a: depot) {
            visitor.visitEntity(a);
        } 
    }

    @Override
    public List<Article> getTopArticles(long brandId, int topSize) {
        final List<Article> as = new ArrayList<Article>();
        int count = 0;
        for (Article a: depot) {
            if (a.getBrandId() == brandId && ++count < topSize) {
                as.add(a);
            }
        }
        return as;
    }

    @Override
    public List<Article> getTopArticles(int topSize) {
        final List<Article> as = new ArrayList<Article>();
        int count = 0;
        for (Article a: depot) {
            if (++count < topSize) {
                as.add(a);
            }
        }
        return as;
    }

    @Override
    public List<Article> getAllArticlesByBrandAndSource(long brandId, long sourceId) {
        final List<Article> as = new ArrayList<Article>();
        for (Article a: depot) {
            if (a.getBrandId() == brandId && a.getSourceId() == sourceId) {
                as.add(a);
            }
        }
        return as;
    }
}
