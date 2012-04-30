package ru.brandanalyst;


import org.joda.time.LocalDateTime;
import org.junit.Test;
import ru.brandanalyst.core.db.provider.EntityVisitor;
import ru.brandanalyst.core.db.provider.mongo.ArticleStorage;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.util.Cf;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: daddy-bear
 * Date: 2/18/12
 * Time: 4:57 PM
 */
public class ArticleStorageTest {

    //remove directory after test


    @Test
    public void testMongoStorage() throws Exception {
        ArticleStorage storage = new ArticleStorage();
        storage.setCollectionName("test");
        storage.setDbName("test");
        storage.setHost("localhost");
        storage.setPort(27017);
        storage.afterPropertiesSet();

        List<Article> s = Cf.newArrayList();
        s.add(new Article(1255L, 1L, "title1", "content1", "3", new LocalDateTime(), 1));
        s.add(new Article(122L, 2L, "title2", "content2", "4", new LocalDateTime(), 2));
        s.add(new Article(123L, 3L, "title3", "content3", "1", new LocalDateTime(), 3));
        s.add(new Article(123L, 3L, "title3", "content3", "1", new LocalDateTime(), 3));

        storage.writeListOfArticlesToDataStore(s);

        s = Cf.newArrayList();
        s.add(new Article(5126L, 1L, "ti sdlkfjlsk lksjdflksj lkjsdlfkjsl lksdjflskjl lskdjflsj  aklsfdjlkj !!!! <<<<<tle1", "content1", "3", new LocalDateTime(), 1));
        s.add(new Article(122L, 2L, "tasdasdasdasqwweqitle2", "conasdasdatent2", "4", new LocalDateTime(), 2));
        s.add(new Article(123L, 3L, "tiasdasdtle3", "content3", "1", new LocalDateTime(), 3));
        s.add(new Article(123L, 3L, "titlaaasswewewsdsdsdssssssse3", "content3", "1", new LocalDateTime(), 3));

        storage.writeListOfArticlesToDataStore(s);

        storage.visitArticles(new EntityVisitor<Article>() {
            @Override
            public void visitEntity(Article e) {
                System.out.println(e.getBrandId());
            }
        });

        storage.destroy();

    }
}
