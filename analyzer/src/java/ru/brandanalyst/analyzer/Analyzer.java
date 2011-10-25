package ru.brandanalyst.analyzer;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.ArticleProvider;
import ru.brandanalyst.core.db.provider.BrandProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.Brand;
import java.util.List;
import org.apache.log4j.Logger;
/**
 * Created by IntelliJ IDEA.
 * User: Nikolaj Karpov
 * Date: 23.10.11
 * Time: 21:10
 */
public class Analyzer implements InitializingBean{
    private static final Logger log = Logger.getLogger(Analyzer.class);

    SimpleJdbcTemplate dirtyJdbcTemplate;
    SimpleJdbcTemplate pureJdbcTemplate;

    void setDirtyJdbcTemplate(SimpleJdbcTemplate dirtyJdbcTemplate){
        this.dirtyJdbcTemplate = dirtyJdbcTemplate;
    }

    void setPureJdbcTemplate(SimpleJdbcTemplate pureJdbcTemplate){
        this.pureJdbcTemplate  = pureJdbcTemplate;
    }

    public void pushBrandsDirtyToPure(){
        BrandProvider from = new BrandProvider(dirtyJdbcTemplate);
        BrandProvider to   = new BrandProvider(pureJdbcTemplate);
        to.writeListOfBrandsToDataStore(from.getAllBrands());
    }

    public void pushArticlesDirtyToPure(){
        ArticleProvider from = new ArticleProvider(dirtyJdbcTemplate);
        ArticleProvider to   = new ArticleProvider(pureJdbcTemplate);
        to.writeListOfArticlesToDataStore(from.getAllArticles());
    }

    public void afterPropertiesSet(){
        log.info("analyzing started...");
        pushArticlesDirtyToPure();
        pushBrandsDirtyToPure();
        //AnalyzerTwitter();
    }
}
