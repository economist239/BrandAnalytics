package ru.brandanalyst.core.db.provider.mysql;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.brandanalyst.core.db.provider.interfaces.BrandDictionaryProvider;
import ru.brandanalyst.core.model.BrandDictionaryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для доступа к словарю тех слов, которые ассоциируются с брендами (изпользуется для сбора новостей)
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/2/11
 * Time: 9:50 PM
 */
public class MySQLBrandDictionaryProvider implements BrandDictionaryProvider {
    private static final Logger log = Logger.getLogger(MySQLBrandDictionaryProvider.class);
    private SimpleJdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Deprecated
    @Override
    public void cleanDataStore() {
        jdbcTemplate.update("TRUNCATE TABLE BrandDictionary");
    }

    @Override
    public BrandDictionaryItem getDictionaryItem(long brandId) {
        SqlRowSet rowSet = jdbcTemplate.getJdbcOperations().queryForRowSet("SELECT BrandId, Brand.Name, Term FROM BrandDictionary INNER JOIN Brand ON BrandId = Brand.Id WHERE BrandId = " + Long.toString(brandId) + " ORDER BY BrandId");
        BrandDictionaryItem dictItem;
        try {
            if (rowSet.next()) {
                String brandName = rowSet.getString("Name");
                dictItem = new BrandDictionaryItem(brandName, brandId);
            } else {
                return null;
            }

            do {
                String item = rowSet.getString("Term");
                dictItem.addItem(item);
            } while (rowSet.next());
            return dictItem;
        } catch (Exception e) {
            log.error("can't get dictionary item from db");
            return null;
        }
    }

    @Override
    public List<BrandDictionaryItem> getDictionary() {

        SqlRowSet rowSet = jdbcTemplate.getJdbcOperations().queryForRowSet("SELECT BrandId, Brand.Name, Term FROM BrandDictionary INNER JOIN Brand ON BrandId = Brand.Id ORDER BY BrandId");
        List<BrandDictionaryItem> dictionary = new ArrayList<BrandDictionaryItem>();

        try {
            long curBrandId;
            if (rowSet.next()) {
                String brandName = rowSet.getString("Name");
                curBrandId = rowSet.getLong("BrandId");
                dictionary.add(new BrandDictionaryItem(brandName, curBrandId));
            } else {
                return null;
            }

            int curBrandNum = 0;
            do {
                long nextBrandId = rowSet.getLong("BrandId");

                if (nextBrandId != curBrandId) {
                    dictionary.add(new BrandDictionaryItem(rowSet.getString("Name"), nextBrandId));
                    curBrandId = nextBrandId;
                    curBrandNum++;
                }
                dictionary.get(curBrandNum).addItem(rowSet.getString("Term"));
            } while (rowSet.next());
            return dictionary;
        } catch (Exception e) {
            log.error("can't get dictionary from db");
            return null;
        }
    }
}
