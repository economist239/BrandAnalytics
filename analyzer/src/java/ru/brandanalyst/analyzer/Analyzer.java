package ru.brandanalyst.analyzer;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.ArticleProvider;
import ru.brandanalyst.core.db.provider.BrandProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.Brand;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nikolaj Karpov
 * Date: 23.10.11
 * Time: 21:10
 */
public class Analyzer implements InitializingBean{
    SimpleJdbcTemplate dirty;
    SimpleJdbcTemplate pure;
    void setDirty(SimpleJdbcTemplate dirty){
        this.dirty = dirty;
    }
    void setPure(SimpleJdbcTemplate pure){
        this.pure  = pure;
    }
    public void pushBrandsDirtyToPure(){
        BrandProvider from = new BrandProvider(dirty);
        BrandProvider to   = new BrandProvider(pure);
        to.writeListOfBrandsToDataStore(from.getAllBrands());
    }
    public void pushArticlesDirtyToPure(){
        ArticleProvider from = new ArticleProvider(dirty);
        ArticleProvider to   = new ArticleProvider(pure);
        to.writeListOfArticlesToDataStore(from.getAllArticles());
    }
    public void afterPropertiesSet(){
        pushArticlesDirtyToPure();
        pushBrandsDirtyToPure();
        void AnalyzerTwitter();
    }
}
