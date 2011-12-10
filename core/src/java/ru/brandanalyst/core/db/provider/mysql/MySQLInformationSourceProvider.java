package ru.brandanalyst.core.db.provider.mysql;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.mapper.InfoSourceMapper;
import ru.brandanalyst.core.db.provider.global.InformationSourceProvider;
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
    private InfoSourceMapper infoSourceMapper;

    public MySQLInformationSourceProvider(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        infoSourceMapper = new InfoSourceMapper();
    }

    @Deprecated
    public void cleanDataStore() {
        jdbcTemplate.update("TRUNCATE TABLE InformationSource");
    }

    public void writeInfoSourceToDataStore(InfoSource infoSource) {
        try {
            jdbcTemplate.update("INSERT INTO InformationSource (TypeId, Title, Description, Website, RSSSource) VALUES(?,?,?,?,?);", Long.toString(infoSource.getSphereId()),
                    infoSource.getTitle(), infoSource.getDescription(), infoSource.getWebsite(), infoSource.getRssSource());
        } catch (Exception e) {
            log.error("cannot wrtie infoSource to db");
        }
    }

    public void writeListOfInfoSourceToDataStore(List<InfoSource> infoSourceList) {
        for (InfoSource i : infoSourceList) {
            writeInfoSourceToDataStore(i);
        }
    }

    public InfoSource getInfoSourceById(long id) {
        List<InfoSource> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM InformationSource WHERE Id = " + Long.toString(id), infoSourceMapper);
        return list.get(0);
    }

    public List<InfoSource> getAllInfoSources() {
        List<InfoSource> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM InformationSource", infoSourceMapper);
        return list;
    }
}
