package ru.brandanalyst.core.db.provider;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;
import org.springframework.beans.factory.DisposableBean;
import ru.brandanalyst.core.model.Article;

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
public class ArticleStorage implements DisposableBean {
    public static final String STORE_NAME = "brand-analytics";
    private final EntityStore storage;
    private final Environment env;
    private final PrimaryIndex<Long, Article> pi;
    private final SecondaryIndex<Long, Long, Article> si;

    public ArticleStorage(String pathName) {
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

        pi = storage.getPrimaryIndex(Long.class, Article.class);
        si = storage.getSecondaryIndex(pi, Long.class, "brand-id-type");
    }

    @Override
    public void destroy() throws Exception {
        storage.close();
        env.close();
    }

    public List<Article> getAllArticles() {
        return null;
    }

    public void writeArticles(List<Article> data) {
        for (Article a: data) {
            pi.putNoReturn(a);
        }
    }

    public void getArticles() {
    }

}
