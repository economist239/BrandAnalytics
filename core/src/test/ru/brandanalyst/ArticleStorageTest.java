package ru.brandanalyst;


import org.junit.Test;
import ru.brandanalyst.core.db.provider.EntityVisitor;
import ru.brandanalyst.core.db.provider.bdb.ArticleKVStorage;
import ru.brandanalyst.core.model.Article;

import java.sql.Date;
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
    public void testBdbStorage() {
        ArticleKVStorage storage = new ArticleKVStorage("~/bdb-test/");
        storage.writeArticleToDataStore(new Article(1L, 1L, "", "", "", new Timestamp(1L), 1));
        storage.writeArticleToDataStore(new Article(2L, 2L, "", "", "", new Timestamp(2L), 2));
        storage.writeArticleToDataStore(new Article(3L, 3L, "", "", "", new Timestamp(3L), 3));

        storage.visitArticles(new EntityVisitor<Article>() {
            @Override
            public void visitEntity(Article e) {
                System.out.println(e.getBrandId());
            }
        });
    }


}
