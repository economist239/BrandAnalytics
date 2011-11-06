package ru.brandanalyst.core.db.provider;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import java.util.List;
import ru.brandanalyst.core.model.InfoSource;
import ru.brandanalyst.core.db.mapper.InfoSourceMapper;
/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/6/11
 * Time: 12:30 PM
 */
public class InformationSourceProvider {
    private static final Logger log = Logger.getLogger(InformationSourceProvider.class);

    private SimpleJdbcTemplate jdbcTemplate; //
    private InfoSourceMapper infoSourceMapper;

    public InformationSourceProvider(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        infoSourceMapper = new InfoSourceMapper();
    }

    public void cleanDataStore() {
        jdbcTemplate.update("TRUNCATE TABLE InformationSource");
    }

    public void writeInfoSourceToDataStore(InfoSource infoSource) {
        try {
            jdbcTemplate.update("INSERT INTO InformationSource (TypeId, Title, Description, Website) VALUES(?,?,?,?);", Long.toString(infoSource.getSphereId()),
                    infoSource.getTitle(), infoSource.getDescription(), infoSource.getWebsite());
        } catch (Exception e) {
            log.error("cannot wrtie infoSource to db");
        }
    }

    public void writeListOfInfoSourceToDataStore(List<InfoSource> infoSourceList) {
        for (InfoSource i: infoSourceList) {
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
