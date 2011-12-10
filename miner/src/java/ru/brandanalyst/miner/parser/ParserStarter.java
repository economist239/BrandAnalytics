package ru.brandanalyst.miner.parser;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.mysql.MySQLInformationSourceProvider;
import ru.brandanalyst.core.model.InfoSource;

import java.util.List;

/**
 * @author OlegPan
 * This class defines what rss channels will be used for information retrieving
 */
public class ParserStarter implements InitializingBean {
protected SimpleJdbcTemplate jdbcTemplate;
    private static final Logger log = Logger.getLogger(ParserStarter.class);
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void afterPropertiesSet() {
        startParsing();
    }
    public void startParsing()
    {
        ParserRSSXML parser=new ParserRSSXML(jdbcTemplate);
        List<InfoSource> infoSources = new MySQLInformationSourceProvider(jdbcTemplate).getAllInfoSources();
        log.info("begin parsing rss");
        for (InfoSource infoSource:infoSources)
        {
            if (infoSource.getSphereId()!=1) continue;
            String rssSource = infoSource.getRssSource();
            if (rssSource.isEmpty()) continue;
            log.info("Parse rss "+rssSource);
            parser.parse(rssSource,infoSource.getId(),0);
        }
        log.info("End parsing rss");

    }
}
