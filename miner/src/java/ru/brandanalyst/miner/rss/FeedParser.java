package ru.brandanalyst.miner.rss;

import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.util.Batch;
import ru.brandanalyst.miner.util.StringChecker;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class FeedParser extends AbstractRssParser {

    public FeedParser(ProvidersHandler providersHandler) {
        super(providersHandler);
    }

    private static final DateFormat FORMATTER = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String LINK = "link";
    private static final String ITEM = "item";
    private static final String PUB_DATE = "pubDate";
    private static final String EMPTY = "";

    public void parse(String feedUrl, long sourceId, Batch<Article> batch) throws XMLStreamException, IOException, ParseException {
        boolean isFeedHeader = false;
        String description = EMPTY;
        String title = EMPTY;
        String link = EMPTY;
        String pubDate = EMPTY;

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = inputFactory.createXMLEventReader(new URL(feedUrl).openStream());

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()) {
                if (event.asStartElement().getName().getLocalPart().equals(ITEM)) {
                    isFeedHeader = true;
                } else if (isFeedHeader) {
                    if (event.asStartElement().getName().getLocalPart().equals(TITLE)) {
                        event = eventReader.nextEvent();
                        title = event.asCharacters().getData();
                        continue;
                    }
                    if (event.asStartElement().getName().getLocalPart().equals(DESCRIPTION)) {
                        event = eventReader.nextEvent();
                        description = event.asCharacters().getData();
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart().equals(LINK)) {
                        event = eventReader.nextEvent();
                        link = event.asCharacters().getData();
                        continue;
                    }
                    if (event.asStartElement().getName().getLocalPart().equals(PUB_DATE)) {
                        event = eventReader.nextEvent();
                        pubDate = event.asCharacters().getData();
                    }
                }
            } else if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(ITEM)) {
                List<Long> brandIds = StringChecker.hasTerm(dictionary, title);
                for (long id : brandIds) {
                    batch.submit(new Article(-1, id, sourceId, title, description,
                            link, new Timestamp(FORMATTER.parse(pubDate).getTime()), NUM_LIKES));
                }
                isFeedHeader = false;
            }
        }
    }
}