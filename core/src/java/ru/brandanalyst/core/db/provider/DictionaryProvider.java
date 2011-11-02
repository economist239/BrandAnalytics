package ru.brandanalyst.core.db.provider;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.brandanalyst.core.model.DictionaryItem;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/2/11
 * Time: 9:50 PM
 */
public class DictionaryProvider {
    private static final Logger log = Logger.getLogger(DictionaryProvider.class);
    private SimpleJdbcTemplate jdbcTemplate;

    public DictionaryProvider(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void cleanDataStore() {
        jdbcTemplate.update("TRUNCATE TABLE Dictionary");
    }

    public DictionaryItem getDictionaryItem(long brandId) {
        SqlRowSet rowSet = jdbcTemplate.getJdbcOperations().queryForRowSet("SELECT (BrandId, Brand.Name, Term) FROM Dictionary INNER JOIN Brand ON BrandId = Brand.Id WHERE BrandId = " + Long.toString(brandId) + " ORDER BY BrandId");
        DictionaryItem dictItem;
        try {
            if (rowSet.next()) {
                String brandName = rowSet.getString("Brand.Name");
                dictItem = new DictionaryItem(brandName,brandId);
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

    public List<DictionaryItem> getDictionary() {

        SqlRowSet rowSet = jdbcTemplate.getJdbcOperations().queryForRowSet("SELECT (BrandId, Brand.Name, Item) FROM Dictionary INNER JOIN Brand ON BrandId = Brand.Id ORDER BY BrandId");
        List<DictionaryItem> dictionary = new ArrayList<DictionaryItem>();

        try {
            long curBrandId;
            if (rowSet.next()) {
                String brandName = rowSet.getString("Brand.Name");
                curBrandId = rowSet.getLong("BrandId");
                dictionary.add(new DictionaryItem(brandName, curBrandId));
            } else {
                return null;
            }

            int curBrandNum = 0;
            do {
                long nextBrandId = rowSet.getLong("BrandId");

                if (nextBrandId != curBrandId) {
                    dictionary.add(new DictionaryItem(rowSet.getString("Brand.Name"), nextBrandId));
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
