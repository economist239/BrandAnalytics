package ru.brandanalyst.analyzer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.ArticleProvider;
import ru.brandanalyst.core.db.provider.BrandProvider;

/**
 * Класс, в котором выполняется весь анализ
 * Created by IntelliJ IDEA.
 * User: Nikolaj Karpov
 * Date: 23.10.11
 * Time: 21:10
 *
 * @version 1.0
 */
public class Analyzer implements InitializingBean {
    private static final Logger log = Logger.getLogger(Analyzer.class);

    /**
     * шаблон первичной БД
     */
    private SimpleJdbcTemplate dirtyJdbcTemplate;
    /**
     * шаблон вторичной БД
     */
    private SimpleJdbcTemplate pureJdbcTemplate;

    /**
     * метод устанавливает связь анализатора и первичной базы данных
     *
     * @param dirtyJdbcTemplate шаблон перичной базы данных
     */
    public void setDirtyJdbcTemplate(SimpleJdbcTemplate dirtyJdbcTemplate) {
        this.dirtyJdbcTemplate = dirtyJdbcTemplate;
    }

    /**
     * метод устанавливает связь анализатора и вторичной базы данных
     *
     * @param pureJdbcTemplate шаблон вторичной базы данных
     */
    public void setPureJdbcTemplate(SimpleJdbcTemplate pureJdbcTemplate) {
        this.pureJdbcTemplate = pureJdbcTemplate;
    }

    /**
     * метод копирует бренды из одной БД в другую
     *
     * @throws NullPointerException
     * @deprecated
     */
    private final void pushBrandsDirtyToPure() {
        BrandProvider from = new BrandProvider(dirtyJdbcTemplate);
        BrandProvider to = new BrandProvider(pureJdbcTemplate);
        to.writeListOfBrandsToDataStore(from.getAllBrands());
    }

    /**
     * метод копирует статьи из одной БД в другую
     *
     * @throws NullPointerException
     * @deprecated
     */
    private final void pushArticlesDirtyToPure() {
        ArticleProvider from = new ArticleProvider(dirtyJdbcTemplate);
        ArticleProvider to = new ArticleProvider(pureJdbcTemplate);
        to.writeListOfArticlesToDataStore(from.getAllArticles());
    }

    /**
     * основной метод анализатора, внутри которого выполняется весь анализ
     */
    public final void afterPropertiesSet() {
        log.info("analyzing started...");
        /*     try {
               pushBrandsDirtyToPure();
               pushArticlesDirtyToPure();
               log.info("db connection successful");
           } catch(NullPointerException e) {
               log.error("db connection error");
               System.exit(1);
           }
        */
        //make graphs for all articles
        GraphsAnalyzer graphsAnalyzer = new GraphsAnalyzer(pureJdbcTemplate, dirtyJdbcTemplate);
        graphsAnalyzer.analyze();

        log.info("analyzing finished succesful");
    }

}
