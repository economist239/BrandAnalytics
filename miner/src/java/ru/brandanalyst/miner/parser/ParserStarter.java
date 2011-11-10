package ru.brandanalyst.miner.parser;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.mapper.InfoSourceMapper;
import ru.brandanalyst.core.db.provider.InformationSourceProvider;
import ru.brandanalyst.core.model.InfoSource;

import java.util.List;

/**
 * @author OlegPan
 * This class defines what rss channels will be used for information retrieving
 */
public class ParserStarter {
protected SimpleJdbcTemplate jdbcTemplate;
    private static final Logger log = Logger.getLogger(ParserStarter.class);
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void afterPropertiesSet() {
        startParsing();
        if (jdbcTemplate==null) System.out.println("Null");
    }
    public void startParsing()
    {
        ParserRSSXML parser=new ParserRSSXML(jdbcTemplate);
        List<InfoSource> infoSources = new InformationSourceProvider(jdbcTemplate).getAllInfoSources();
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
