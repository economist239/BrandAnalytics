package ru.brandanalyst.analyzer;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.ArticleProvider;
import ru.brandanalyst.core.db.provider.BrandProvider;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Nikolaj Karpov
 * Date: 23.10.11
 * Time: 21:10
 */
public class Analyzer implements InitializingBean {
    private static final Logger log = Logger.getLogger(Analyzer.class);

    private SimpleJdbcTemplate dirtyJdbcTemplate;
    private SimpleJdbcTemplate pureJdbcTemplate;

    public void setDirtyJdbcTemplate(SimpleJdbcTemplate dirtyJdbcTemplate) {
        this.dirtyJdbcTemplate = dirtyJdbcTemplate;
    }

    public void setPureJdbcTemplate(SimpleJdbcTemplate pureJdbcTemplate) {
        this.pureJdbcTemplate = pureJdbcTemplate;
    }

    private final void pushBrandsDirtyToPure() throws NullPointerException {
        BrandProvider from = new BrandProvider(dirtyJdbcTemplate);
        BrandProvider to = new BrandProvider(pureJdbcTemplate);
        to.writeListOfBrandsToDataStore(from.getAllBrands());
    }

    private final void pushArticlesDirtyToPure() throws NullPointerException {
        ArticleProvider from = new ArticleProvider(dirtyJdbcTemplate);
        ArticleProvider to = new ArticleProvider(pureJdbcTemplate);
        to.writeListOfArticlesToDataStore(from.getAllArticles());
    }

    public final void afterPropertiesSet() {
        log.info("analyzing started...");
        try {
            pushBrandsDirtyToPure();
            pushArticlesDirtyToPure();
        } catch(NullPointerException e) {
            log.error("db connection error");
            System.exit(1);
        }

        //makes graph for all articles
        //GraphsAnalyzer graphsAnalyzer = new GraphsAnalyzer(pureJdbcTemplate, dirtyJdbcTemplate);
        //graphsAnalyzer.analyze();
        log.info("analyzing finished succesful");
    }

}
