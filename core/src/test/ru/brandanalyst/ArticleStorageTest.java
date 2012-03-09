package ru.brandanalyst;


import org.junit.Test;
import ru.brandanalyst.core.db.provider.EntityVisitor;
import ru.brandanalyst.core.db.provider.bdb.ArticleKVStorage;
import ru.brandanalyst.core.db.provider.mongo.ArticleStorage;
import ru.brandanalyst.core.model.Article;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: daddy-bear
 * Date: 2/18/12
 * Time: 4:57 PM
 */
public class ArticleStorageTest {

    //remove directory after test
    @Test
    public void testBdbStorage() throws Exception {
        ArticleKVStorage storage = new ArticleKVStorage("~/bdb-test/");
        storage.writeArticleToDataStore(new Article(1L, 1L, "title1", "content1", "3", new Timestamp(1L), 1));
        storage.writeArticleToDataStore(new Article(2L, 2L, "title2", "content2", "4", new Timestamp(2L), 2));
        storage.writeArticleToDataStore(new Article(3L, 3L, "title3", "content3", "1", new Timestamp(3L), 3));

        storage.visitArticles(new EntityVisitor<Article>() {
            @Override
            public void visitEntity(Article e) {
                System.out.println(e.getBrandId());
            }
        });

        storage.destroy();

    }


    @Test
    public void testMongoStorage() throws Exception {
        ArticleStorage storage = new ArticleStorage("localhost", 27017);
        storage.writeArticleToDataStore(new Article(1L, 1L, "title1", "content1", "3", new Timestamp(1L), 1));
        storage.writeArticleToDataStore(new Article(2L, 2L, "title2", "content2", "4", new Timestamp(2L), 2));
        storage.writeArticleToDataStore(new Article(3L, 3L, "title3", "content3", "1", new Timestamp(3L), 3));
        storage.writeArticleToDataStore(new Article(3L, 3L, "title3", "content3", "1", new Timestamp(3L), 3));

        storage.visitArticles(new EntityVisitor<Article>() {
            @Override
            public void visitEntity(Article e) {
                System.out.println(e.getBrandId());
            }
        });

        storage.destroy();

    }

    @Test
    public void testShow() throws Exception {
        ArticleStorage storage = new ArticleStorage("localhost", 27017);
        storage.seeArticles(new EntityVisitor<Article>() {
            @Override
            public void visitEntity(Article e) {
                System.out.println(e.getSourceId() + " " + e.getBrandId() + " " + e.getTitle() );
            }
        });
    }
}
