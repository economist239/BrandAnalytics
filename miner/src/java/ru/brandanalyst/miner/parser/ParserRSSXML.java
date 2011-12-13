package ru.brandanalyst.miner.parser;


import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.db.provider.interfaces.ArticleProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.BrandDictionaryItem;
import ru.brandanalyst.miner.util.StringChecker;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author OlegPan
 *         This class retrives information from rss channels
 */
public class ParserRSSXML {
    private static final Logger log = Logger.getLogger(ParserRSSXML.class);
    private ArticleProvider articleProvider;
    private List<BrandDictionaryItem> dictionary;

    public ParserRSSXML(ProvidersHandler providersHandler) {
        articleProvider = providersHandler.getArticleProvider();
        dictionary = providersHandler.getBrandDictionaryProvider().getDictionary();
    }

    private Timestamp evalTimestamp(String stringDate) throws StringIndexOutOfBoundsException {
        StringTokenizer dateTokenizer = new StringTokenizer(stringDate);
        StringBuilder resultDate = new StringBuilder();
        dateTokenizer.nextToken();
        resultDate.append(dateTokenizer.nextToken());
        switch (dateTokenizer.nextToken()) {
            case "Jan":
                resultDate.append("01");
                break;
            case "Feb":
                resultDate.append("02");
                break;
            case "Mar":
                resultDate.append("03");
                break;
            case "Apr":
                resultDate.append("04");
                break;
            case "May":
                resultDate.append("05");
                break;
            case "Jun":
                resultDate.append("06");
                break;
            case "Jul":
                resultDate.append("07");
                break;
            case "Aug":
                resultDate.append("08");
                break;
            case "Sep":
                resultDate.append("09");
                break;
            case "Oct":
                resultDate.append("10");
                break;
            case "Nov":
                resultDate.append("11");
                break;
            case "Dec":
                resultDate.append("12");
                break;
        }
        resultDate.append(dateTokenizer.nextToken());
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        Date date;
        try {
            date = dateFormat.parse(resultDate.toString());
        } catch (ParseException e) {
            date = new Date();
        }

        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

    public void parse(String url, long sourceId, int numOfLikes) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url);
            Element root = doc.getDocumentElement();
            NodeList articles = root.getElementsByTagName("item");
            for (int i = 0, n = articles.getLength(); i < n; i++) {
                Element element = (Element) articles.item(i);
                String articleTitle = element.getElementsByTagName("title").item(0).getTextContent();
                List<Long> brandIds = StringChecker.hasTerm(dictionary, articleTitle);

                if (brandIds.isEmpty()) continue;
                String articleLink = element.getElementsByTagName("link").item(0).getTextContent();
                String articleText = element.getElementsByTagName("description").item(0).getTextContent();
                Timestamp articleTimestamp = evalTimestamp(element.getElementsByTagName("pubDate").item(0).getTextContent());
                for (Long brandId : brandIds) {
                    Article article = new Article(-1, brandId, sourceId, articleTitle, articleText, articleLink, articleTimestamp, numOfLikes);
                    articleProvider.writeArticleToDataStore(article);
                }
            }
        } catch (Exception e) {
            log.error("Error on parse xml", e);
        }

    }
}
