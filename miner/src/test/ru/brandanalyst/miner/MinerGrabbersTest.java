package ru.brandanalyst.miner;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import ru.brandanalyst.core.db.provider.*;
import ru.brandanalyst.core.model.BrandDictionaryItem;
import ru.brandanalyst.miner.util.*;
import java.util.Date;
import java.util.List;

/**
 * Author: Olga Bolshakova (obolshakova@yandex-team.ru)
 * Date: 02.01.11 18:09
 */
public class MinerGrabbersTest extends AbstractDependencyInjectionSpringContextTests {

    private SimpleJdbcTemplate jdbcTemplate;

    private ArticleProvider articleProvider;
    private BrandProvider brandProvider;

    private GrabberTwitter grabberTwitter;
    private GrabberRia grabberRia;

    public MinerGrabbersTest() {
        super();
        setAutowireMode(AUTOWIRE_BY_NAME);
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{
                "classpath:test-config.xml"
        };
    }

    @Required
    public void setJdbcTemplate(final SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        brandProvider = new BrandProvider(jdbcTemplate);
        articleProvider = new ArticleProvider(jdbcTemplate);
    }

    @Required
    public void setGrabberRia(final GrabberRia grabberRia) {
        this.grabberRia = grabberRia;
    }

    @Required
    public void setGrabberTwitter(final GrabberTwitter grabberTwitter) {
        this.grabberTwitter = grabberTwitter;
    }

    public void testExists() {
        assertNotNull(jdbcTemplate);
        assertNotNull(articleProvider);
        assertNotNull(brandProvider);
        assertNotNull(grabberRia);
        assertNotNull(grabberTwitter);
    }

    public void testTwitter() throws Exception {
        grabberTwitter.grab(new Date(new Date().getTime() - 4 * (long) 86400000));
        articleProvider.cleanDataStore();
    }

    public void testRia() throws Exception {
        grabberRia.grab(new Date(new Date().getTime() - (long) 86400000));
        articleProvider.cleanDataStore();
    }

    public void testUtil() throws Exception {
        BrandDictionaryProvider brandDictionaryProvider = new BrandDictionaryProvider(jdbcTemplate);
        List<BrandDictionaryItem> dictionary = brandDictionaryProvider.getDictionary();
        assertTrue(StringChecker.hasTerm(dictionary.get(0), "Microsoft DJhkjdzfvljbfv aofhvkjah"));
        assertFalse(StringChecker.hasTerm(dictionary.get(1), "Microsoft DJhkjdzfvljbfv aofhvkjah"));
    }
}
