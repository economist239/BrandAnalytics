package ru.brandanalyst.core.db.provider.mysql;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.mapper.SemanticDictionaryMapper;
import ru.brandanalyst.core.db.provider.interfaces.SemanticDictinaryProvider;
import ru.brandanalyst.core.model.SemanticDictionaryItem;

import java.util.List;
import java.util.HashSet;

/**
 * Класс доступа к словарю для эмоциональной окраски текста, использеутся в модуле analyzer
 * Created by IntelliJ IDEA.
 * User: DmitryBatkovich
 * Date: 11/4/11
 * Time: 9:20 AM
 */
public class MySQLSemanticDictionaryProvider implements SemanticDictinaryProvider {
    private static final Logger log = Logger.getLogger(MySQLSemanticDictionaryProvider.class);
    private SimpleJdbcTemplate jdbcTemplate;
    private SemanticDictionaryMapper semanticDictMapper;

    public MySQLSemanticDictionaryProvider(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        semanticDictMapper = new SemanticDictionaryMapper();
    }

    @Deprecated
    public void cleanDataStore() {
        jdbcTemplate.update("TRUNCATE TABLE SemanticDictionary");
    }

    public HashSet<SemanticDictionaryItem> getSemanticDictionary() {
        List<SemanticDictionaryItem> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM SemanticDictionary", semanticDictMapper);
        return new HashSet<SemanticDictionaryItem>(list);
    }

    public void setSemanticDictionaryItem(SemanticDictionaryItem item) {
        try {
            jdbcTemplate.update("INSERT INTO SemanticDictionary (Term, SemanticValue) VALUES(?,?);", item.getTerm(), item.getSemanticValue());
        } catch (DataAccessException e) {
            log.error("cannot wrtie item to db");
        }
    }
}
