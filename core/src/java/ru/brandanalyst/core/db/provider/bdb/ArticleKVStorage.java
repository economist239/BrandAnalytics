package ru.brandanalyst.core.db.provider.bdb;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.*;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Required;
import ru.brandanalyst.core.db.provider.EntityVisitor;
import ru.brandanalyst.core.db.provider.interfaces.ArticleProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.ArticleForWeb;

import java.io.File;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: daddy-bear
 * Date: 2/18/12
 * Time: 1:30 PM
 * <p/>
 * key-value storage for mined articles
 */
public class ArticleKVStorage extends ArticleProvider implements DisposableBean {

    private final static int BATCH_SIZE = 1024;
    private final static Logger log = Logger.getLogger(ArticleKVStorage.class);
    private static final String STORE_NAME = "brand-analytics";
    private final EntityStore storage;
    private final Environment env;
    private boolean eraseDataAfterVisit;
    private final PrimaryIndex<String, ArticleEntity> pi;
    private final SecondaryIndex<Long, String, ArticleEntity> si;

    public ArticleKVStorage(String pathName) {
        File path = new File(pathName);
        if (!path.exists()) {
            if (!path.mkdirs()) {
                throw new RuntimeException("fail path \"" + pathName + "\" creation. BDB");
            }
        }

        EnvironmentConfig envConfig = new EnvironmentConfig();
        StoreConfig storeConfig = new StoreConfig();

        envConfig.setAllowCreate(true);
        storeConfig.setAllowCreate(true);

        envConfig.setTransactional(false);
        storeConfig.setTransactional(false);

        envConfig.setReadOnly(false);
        storeConfig.setReadOnly(false);

        storeConfig.setTemporary(false);

        env = new Environment(path, envConfig);
        storage = new EntityStore(env, STORE_NAME, storeConfig);

        pi = storage.getPrimaryIndex(String.class, ArticleEntity.class);
        si = storage.getSecondaryIndex(pi, Long.class, "brandId");
    }

    @Required
    public void setEraseDataAfterVisit(boolean eraseDataAfterVisit) {
        this.eraseDataAfterVisit = eraseDataAfterVisit;
    }

    @Override
    public void writeArticleToDataStore(Article article) {
        pi.putNoReturn(new ArticleEntity(article));
    }

    @Override
    public void writeListOfArticlesToDataStore(List<Article> articles) {
        for (Article a : articles) {
            pi.putNoReturn(new ArticleEntity(a));
        }
    }

    @Override
    public Article getArticleBySourceId(long sourceId) {
        throw new UnsupportedOperationException("unsupported operation in DBD STORAGE");
    }

    @Override
    public List<Article> getAllArticlesBySourceId(long sourceId) {
        throw new UnsupportedOperationException("unsupported operation in DBD STORAGE");
    }

    @Override
    public Article getArticleById(long articleId) {
        throw new UnsupportedOperationException("unsupported operation in DBD STORAGE");
    }

    @Override
    public ArticleForWeb getArticleForWebById(long articleId) {
        throw new UnsupportedOperationException("unsupported operation in DBD STORAGE");
    }

    @Override
    public List<Article> getAllArticlesByBrand(long brandId) {
        throw new UnsupportedOperationException("unsupported operation in DBD STORAGE");
    }

    @Override
    public List<Article> getAllOfficialArticlesByBrand(long brandId) {
        throw new UnsupportedOperationException("unsupported operation in DBD STORAGE");
    }

    @Override
    public List<Article> getArticlesWithCondition(String whereClause) {
        throw new UnsupportedOperationException("unsupported operation in DBD STORAGE");
    }

    @Override
    public List<Article> getTopArticles(long brandId, int topSize) {
        throw new UnsupportedOperationException("unsupported operation in DBD STORAGE");
    }

    @Override
    public List<Article> getAllArticlesByBrandAndSource(long brandId, long sourceId) {
        throw new UnsupportedOperationException("unsupported operation in DBD STORAGE");
    }

    @Override
    public void setAnalyzed(List<Long> ids) {
        throw new UnsupportedOperationException("unsupported operation in DBD STORAGE");
    }

    @Override
    public List<Article> getOnlyNotAnalyzedArticles() {
        throw new UnsupportedOperationException("unsupported operation in DBD STORAGE");
    }

    @Override
    public void destroy() throws Exception {
        storage.close();
        env.close();
    }

    @Override
    public List<Article> getAllArticles() {
        return null;
    }

    @Override
    public void visitArticles(EntityVisitor<Article> visitor) {
        EntityCursor<ArticleEntity> cursor = pi.entities();
//        for (int i = 0; i < cursor.count(); i++) {
            visitor.visitEntity(cursor.next().getArticle());
  //      }
        /*   if (eraseDataAfterVisit) {
            EntityCursor<Long> keys = pi.keys();
            for (int i = 0; i < keys.count(); i++) {
                pi.delete(keys.next());
            }
        }*/
    }
}
