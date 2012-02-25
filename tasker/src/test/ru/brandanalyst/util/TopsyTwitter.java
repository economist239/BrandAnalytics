package ru.brandanalyst.util;

/*
 *  Date: 21.02.12
 *  Time: 20:57
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class TopsyTwitter {
    private static final Logger log = Logger.getLogger(TopsyTwitter.class);

    private final int maxCount;
    public static final int DEFAULT_MAX_COUNT = 150;

    public TopsyTwitter() {
        this(DEFAULT_MAX_COUNT);
    }

    public TopsyTwitter(int maxCount) {
        this.maxCount = maxCount;
    }

    public TopsyItem getTweets(String query) throws IOException, XPatherException {
        String last = "d";
        boolean isEmpty = false;
        TopsyItem topsyItem = new TopsyItem(query);
        int count = 1;
        while (!isEmpty && topsyItem.tweets.size() < maxCount) {
            String url = String.format("http://topsy.com/s/%s/tweet?allow_lang=ru&page=%d&window=%s", URLEncoder.encode(query, "UTF-8"), count++, last);
            TagNode tagNode = XmlProvider.getTagNode(new URL(url));
            Object found[] = tagNode.evaluateXPath("//DIV[@class='list']/DIV/DIV/DIV/SPAN");
            if (found.length == 0) {
                isEmpty = true;
            }
            for (Object item : found) {
                TagNode node = (TagNode) item;
                topsyItem.tweets.add(TagNodeUtil.getText(node));
            }
        }
        return topsyItem;
    }

    private static class TopsyItem {
        public final String query;
        public final Collection<String> tweets = new LinkedHashSet<String>();

        public TopsyItem(String query) {
            this.query = query;
        }
    }

    public static void main(String[] args) throws XPatherException, IOException {
        String companies[] = {
                "google",
                "Сбербанк",
                "Газпром",
                "ВТБ",
                "Роснефть",
                "ЛУКОЙЛ",
                "НорНикель",
                "РусГидро",
                "НЛМК",
                "НОВАТЭК",
                "Сургутнефтегаз",
                "Татнефть",
                "МТС",
                "Полюс-Золото",
                "Полиметалл",
                "Газпрнефть",
                "Акрон",
                "Автоваз",
                "Соллерс",
                "КАМАЗ",
                "ОГК - 3"
        };

        log.info("Mining topsy started");
        List<TopsyItem> companiesTweets = new ArrayList<TopsyItem>();
        for (String company : companies) {
            log.info("Company now: " + company) ;
            TopsyItem topsyItem = new TopsyTwitter().getTweets(company);
            companiesTweets.add(topsyItem);
            log.info("Company done; " + company + " Found: " + topsyItem.tweets.size());
        }
        log.info("Mining topsy ended");

        XStream xStream = new XStream();
        xStream.toXML(companiesTweets, new FileWriter("tweets.xml"));
    }
}
