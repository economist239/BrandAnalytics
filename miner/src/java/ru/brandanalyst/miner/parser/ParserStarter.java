package ru.brandanalyst.miner.parser;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.mysql.MySQLInformationSourceProvider;
import ru.brandanalyst.core.model.InfoSource;
import ru.brandanalyst.miner.Grabber;

import java.util.Date;
import java.util.List;

/**
 * @author OlegPan
 *         This class defines what rss channels will be used for information retrieving
 */
public class ParserStarter extends Grabber {
    private static final Logger log = Logger.getLogger(ParserStarter.class);

    public void grab(Date date) {
        ParserRSSXML parser = new ParserRSSXML(dirtyProvidersHandler);
        List<InfoSource> infoSources = dirtyProvidersHandler.getInformationSourceProvider().getAllInfoSources();
        log.info("begin parsing rss");
        for (InfoSource infoSource : infoSources) {
            if (infoSource.getSphereId() != 1) continue;
            String rssSource = infoSource.getRssSource();
            if (rssSource.isEmpty()) continue;
            log.info("Parse rss " + rssSource);
            parser.parse(rssSource, infoSource.getId(), 0);
        }
        log.info("End parsing rss");

    }
}
