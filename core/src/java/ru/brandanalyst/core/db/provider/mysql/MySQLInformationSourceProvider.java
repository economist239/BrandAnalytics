package ru.brandanalyst.core.db.provider.mysql;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.interfaces.InformationSourceProvider;
import ru.brandanalyst.core.model.InfoSource;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/6/11
 * Time: 12:30 PM
 */
public class MySQLInformationSourceProvider implements InformationSourceProvider {
    private SimpleJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public InfoSource getInfoSourceById(long id) {
        return jdbcTemplate.query("SELECT * FROM InformationSource WHERE Id =? ",
                MappersHolder.INFO_SOURCE_MAPPER, id).get(0);
    }

    @Override
    public List<InfoSource> getAllInfoSources() {
        return jdbcTemplate.query("SELECT * FROM InformationSource",
                MappersHolder.INFO_SOURCE_MAPPER);
    }
}
