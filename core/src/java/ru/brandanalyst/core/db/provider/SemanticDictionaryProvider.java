package ru.brandanalyst.core.db.provider;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.model.SemanticDictionaryItem;
import ru.brandanalyst.core.db.mapper.SemanticDictionaryMapper;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DmitryBatkovich
 * Date: 11/4/11
 * Time: 9:20 AM
 */
public class SemanticDictionaryProvider {
    private static final Logger log = Logger.getLogger(SemanticDictionaryProvider.class);
    private SimpleJdbcTemplate jdbcTemplate;
    private SemanticDictionaryMapper semanticDictMapper;

    public SemanticDictionaryProvider(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        semanticDictMapper = new SemanticDictionaryMapper();
    }

    public void cleanDataStore() {
        jdbcTemplate.update("TRUNCATE TABLE SemanticDictionary");
    }

    public List<SemanticDictionaryItem> getSemanticDictionary() {
        List<SemanticDictionaryItem> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM SemanticDictionary", semanticDictMapper);
        return list;
    }

    public void setSemanticDictionary(SemanticDictionaryItem item) {
        try {
            jdbcTemplate.update("INSERT INTO SemanticDictionary (Term, SemanticValue) VALUES(?,?);", item.getTerm(), item.getSemanticValue());
        } catch (DataAccessException e) {
            log.error("cannot wrtie item to db");
        }
    }
}
