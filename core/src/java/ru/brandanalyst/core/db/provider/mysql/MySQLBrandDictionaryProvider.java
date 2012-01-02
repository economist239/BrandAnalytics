package ru.brandanalyst.core.db.provider.mysql;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.interfaces.BrandDictionaryProvider;
import ru.brandanalyst.core.model.BrandDictionaryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    private SimpleJdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BrandDictionaryItem getDictionaryItem(long brandId) {
        final BrandDictionaryItem dictItem = new BrandDictionaryItem(brandId);
        jdbcTemplate.getJdbcOperations().query("SELECT BrandId, Brand.Name, Term FROM BrandDictionary INNER JOIN Brand ON BrandId = Brand.Id WHERE BrandId =?", new Object[]{brandId}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                dictItem.addItem(rs.getString("Term"));
            }
        });

        return dictItem;
    }

    @Override
    public List<BrandDictionaryItem> getDictionary() {
        final List<BrandDictionaryItem> dictionary = new ArrayList<BrandDictionaryItem>();

        jdbcTemplate.getJdbcOperations().query("SELECT BrandId, Term FROM BrandDictionary INNER JOIN Brand ON BrandId = Brand.Id ORDER BY BrandId", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                long brandId = rs.getLong("BrandId");
                BrandDictionaryItem item;
                if (dictionary.size() == 0 || dictionary.get(dictionary.size() - 1).getBrandId() != brandId) {
                    item = new BrandDictionaryItem(brandId);
                } else {
                    item = dictionary.get(dictionary.size() - 1);
                }
                item.addItem(rs.getString("Term"));
                dictionary.add(item);
            }
        });

        return dictionary;
    }
}