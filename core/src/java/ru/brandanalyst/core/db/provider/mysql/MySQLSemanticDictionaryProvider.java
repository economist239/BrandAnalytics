package ru.brandanalyst.core.db.provider.mysql;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.interfaces.SemanticDictionaryProvider;
import ru.brandanalyst.core.model.SemanticDictionaryItem;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс доступа к словарю для эмоциональной окраски текста, использеутся в модуле analyzer
 * Created by IntelliJ IDEA.
 * User: DmitryBatkovich
 * Date: 11/4/11
 * Time: 9:20 AM
 */
public class MySQLSemanticDictionaryProvider implements SemanticDictionaryProvider {
    private SimpleJdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Set<SemanticDictionaryItem> getSemanticDictionary() {
        return new HashSet<SemanticDictionaryItem>(jdbcTemplate.query("SELECT * FROM SemanticDictionary",
                MappersHolder.SEMANTIC_DICTIONARY_MAPPER));
    }

    @Override
    public void setSemanticDictionaryItem(SemanticDictionaryItem item) {
        jdbcTemplate.update("INSERT INTO SemanticDictionary (Term, SemanticValue) VALUES(?,?)", item.getTerm(), item.getSemanticValue());
    }
}
