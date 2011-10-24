import ru.brandanalyst.core.db.provider.BrandProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.db.provider.ArticleProvider;
import ru.brandanalyst.core.model.Article;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.apache.commons.dbcp.BasicDataSource;
import ru.brandanalyst.indexer.Indexer;
import ru.brandanalyst.searcher.Searcher;

import java.sql.Timestamp;
import java.util.List;
import java.sql.Timestamp;
/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/12/11
 * Time: 7:09 PM
 */
public class Test {    //this is worked test for lucene index and search
    //this test not work while not add base of articles
    public static void main(String[] args) {

        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/BAdirty?useUnicode=true&amp;characterEncoding=utf8");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setValidationQuery("select 1");
        SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(ds);
        BrandProvider dataStore = new BrandProvider(jdbcTemplate);

        dataStore.cleanDataStore();
        Brand b1 = new Brand(4,"Gazprom","Gazprom is russian gasoline gaint","www.gazprom.ru",0);
        dataStore.writeBrandToDataStore(b1);
        b1 = new Brand(1,"Microsoft","Microsoft makes bad software","www.microsoft.com",0);
        dataStore.writeBrandToDataStore(b1);
        b1 = new Brand(3,"Apple","Apple makes software too and i-production. На русском","www.apple.com",0);
        dataStore.writeBrandToDataStore(b1);
        b1 = new Brand(2,"Google","Google is better than other search machines","www.google.com",0);
        dataStore.writeBrandToDataStore(b1);

        ArticleProvider dataStore2 = new ArticleProvider(jdbcTemplate);
        Article a1 = new Article(4,1,1,"Gazprom crashed","Apple делает хорошую продукцию","www.lenta.ru",new Timestamp(90,0,0,0,0,0,0),0);
        dataStore2.writeArticleToDataStore(a1);
        a1 = new Article(2,1,1,"Microsoft crashed","Apple не делает хорошую продукцию","www.lenta.ru",new Timestamp(90,0,0,0,0,0,0),0);
        dataStore2.writeArticleToDataStore(a1);

        Indexer ind = new Indexer();
        ind.setDirectoryBrand("index_brand/");
        ind.setDirectoryArticle("index_article/"); // while not work's sorry... can add base of articles...
        ind.setJdbcTemplate(jdbcTemplate); // set base of brand and base of articles
        try{
            ind.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Searcher searcher = new Searcher();
        searcher.setIndexDirArticle("index_article/");
        searcher.setIndexDirBrand("index_brand/");
        try{
            searcher.getReadyForSearch();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            List<Article> lst = searcher.searchArticleByContent("делает");
            for(Article b: lst) {
                System.out.println(b.getContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            List<Brand> lst = searcher.searchBrandByDescription("software");
            for(Brand b: lst) {
                System.out.println(b.getDescription());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
