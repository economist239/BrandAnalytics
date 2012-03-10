package ru.brandanalyst.core.db.provider.mongo;

import com.mongodb.*;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.DisposableBean;
import ru.brandanalyst.core.db.provider.EntityVisitor;
import ru.brandanalyst.core.db.provider.interfaces.ArticleProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.ArticleForWeb;

import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/19/12
 * Time: 7:35 PM
 */
public class ArticleStorage extends ArticleProvider implements DisposableBean {
    private final static int BATCH_SIZE = 1024;
    private final static String DB_NAME = "ba-dirty";
    private final static String COLLECTION_NAME = "article-collection";
    public static final String MONGO_ID = "_id";
    private final Mongo mongo;
    private final DB db;
    private final DBCollection coll;



    public ArticleStorage(String host, int port) {
        try {
            mongo = new Mongo(host, port);
            db = mongo.getDB(DB_NAME);
            coll = db.getCollection(COLLECTION_NAME);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<DBObject> wrap(List<Article> articles) {
        List<DBObject> objects = new ArrayList<DBObject>(articles.size());
        for (Article a : articles) {
            objects.add(wrap(a));
        }
        return objects;
    }

    private static DBObject wrap(Article a) {
        BasicDBObject object = new BasicDBObject();
        object.put(MONGO_ID, a.hashCode());
        object.put("id", a.getId());
        object.put("brand-id", a.getBrandId());
        object.put("source-id", a.getSourceId());
        object.put("title", a.getTitle());
        object.put("content", a.getContent());
        object.put("link", a.getLink());
        object.put("num-likes", a.getNumLikes());
        object.put("tstamp", a.getTstamp().toDate().getTime());
        return object;
    }

    private static List<Article> unwrap(List<BasicDBObject> objects) {
        List<Article> articles = new ArrayList<Article>(objects.size());
        for (BasicDBObject o : objects) {
            articles.add(unwrap(o));
        }
        return articles;
    }

    private static Article unwrap(DBObject object) {
        return new Article(Long.parseLong(object.get("id").toString()), Long.parseLong(object.get("brand-id").toString()),
                Long.parseLong(object.get("source-id").toString()), object.get("title").toString(),
                object.get("content").toString(), object.get("link").toString(),
                new LocalDateTime(Long.parseLong(object.get("tstamp").toString())), Integer.parseInt(object.get("num-likes").toString()));

    }

    @Override
    public void writeArticleToDataStore(Article article) {
        coll.insert(wrap(article));
    }

    @Override
    public void writeListOfArticlesToDataStore(List<Article> articles) {
        coll.insert(wrap(articles));
    }

    @Override
    public Article getArticleBySourceId(long sourceId) {
        throw new UnsupportedOperationException("unsupported method in MONGO DB");
    }

    @Override
    public List<Article> getAllArticlesBySourceId(long sourceId) {
        throw new UnsupportedOperationException("unsupported method in MONGO DB");

    }

    @Override
    public Article getArticleById(long articleId) {
        throw new UnsupportedOperationException("unsupported method in MONGO DB");
    }

    @Override
    public ArticleForWeb getArticleForWebById(long articleId) {
        throw new UnsupportedOperationException("unsupported method in MONGO DB");
    }

    @Override
    public List<Article> getAllArticlesByBrand(long brandId) {
        throw new UnsupportedOperationException("unsupported method in MONGO DB");
    }

    @Override
    public List<Article> getAllOfficialArticlesByBrand(long brandId) {
        throw new UnsupportedOperationException("unsupported method in MONGO DB");
    }

    @Override
    public List<Article> getAllArticles() {
        throw new UnsupportedOperationException("unsupported method in MONGO DB");
    }

    @Override
    public List<Article> getArticlesWithCondition(String whereClause) {
        throw new UnsupportedOperationException("unsupported method in MONGO DB");
    }

    @Override
    public void visitArticles(EntityVisitor<Article> visitor) {
        seeArticles(visitor);
        coll.drop();
    }

    public void seeArticles(EntityVisitor<Article> visitor) {
        DBCursor cursor = coll.find().batchSize(BATCH_SIZE);
        while (cursor.hasNext()) {
            DBObject next = cursor.next();
            visitor.visitEntity(unwrap(next));
        }
    }

    @Override
    public List<Article> getTopArticles(long brandId, int topSize) {
        throw new UnsupportedOperationException("unsupported method in MONGO DB");
    }

    @Override
    public List<Article> getTopArticles(int topSize) {
        throw new UnsupportedOperationException("unsupported method in MONGO DB");
    }

    @Override
    public List<Article> getAllArticlesByBrandAndSource(long brandId, long sourceId) {
        throw new UnsupportedOperationException("unsupported method in MONGO DB");
    }

    @Override
    public void setAnalyzed(List<Long> ids) {
        throw new UnsupportedOperationException("unsupported method in MONGO DB");
    }

    @Override
    public List<Article> getOnlyNotAnalyzedArticles() {
        throw new UnsupportedOperationException("unsupported method in MONGO DB");
    }

    @Override
    public void destroy() throws Exception {
        mongo.close();
    }


}
