package ru.brandanalyst.core.db.provider.mysql;

import org.apache.log4j.Logger;
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
    private static final Logger log = Logger.getLogger(MySQLInformationSourceProvider.class);

    private SimpleJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Deprecated
    public void cleanDataStore() {
        jdbcTemplate.update("TRUNCATE TABLE InformationSource");
    }

    @Override
    public void writeInfoSourceToDataStore(InfoSource infoSource) {
        try {
            jdbcTemplate.update("INSERT INTO InformationSource (TypeId, Title, Description, Website, RSSSource) VALUES(?,?,?,?,?);", Long.toString(infoSource.getSphereId()),
                    infoSource.getTitle(), infoSource.getDescription(), infoSource.getWebsite(), infoSource.getRssSource());
        } catch (Exception e) {
            log.error("cannot wrtie infoSource to db");
        }
    }

    @Override
    public void writeListOfInfoSourceToDataStore(List<InfoSource> infoSourceList) {
        for (InfoSource i : infoSourceList) {
            writeInfoSourceToDataStore(i);
        }
    }

    @Override
    public InfoSource getInfoSourceById(long id) {
        List<InfoSource> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM InformationSource WHERE Id = " +
                Long.toString(id), MappersHolder.INFO_SOURCE_MAPPER);
        return list.get(0);
    }

    @Override
    public List<InfoSource> getAllInfoSources() {
        List<InfoSource> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM InformationSource",
                MappersHolder.INFO_SOURCE_MAPPER);
        return list;
    }
}
