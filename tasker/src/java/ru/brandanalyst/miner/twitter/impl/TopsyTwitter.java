package ru.brandanalyst.miner.twitter.impl;

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
import ru.brandanalyst.miner.twitter.ITwitter;
import ru.brandanalyst.miner.twitter.ITwitterException;
import ru.brandanalyst.miner.util.TagNodeUtil;
import ru.brandanalyst.miner.util.XmlProvider;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;


public class TopsyTwitter implements ITwitter {
    private static final Logger log = Logger.getLogger(TopsyTwitter.class);

    private final int maxCount;
    public static final int DEFAULT_MAX_COUNT = 150;

    public TopsyTwitter() {
        this(DEFAULT_MAX_COUNT);
    }

    public TopsyTwitter(int maxCount) {
        this.maxCount = maxCount;
    }

    public TwitterResultItem getPopularTweets(String query) throws ITwitterException {
        String last = "w";
        boolean isEmpty = false;
        TwitterResultItem topsyItem = new TwitterResultItem(query);
        int count = 1;
        while (!isEmpty && topsyItem.tweets.size() < maxCount) {
            TagNode tagNode;
            try {
                String url = String.format("http://topsy.com/s/%s/tweet?allow_lang=ru&page=%d&window=%s", URLEncoder.encode(query, "UTF-8"), count++, last);
                tagNode = XmlProvider.getTagNode(new URL(url));
            } catch (IOException e) {
                log.error("IOProblem", e);
                throw new ITwitterException("IOProblem", e);
            }

            Object found[];
            try {
                found = tagNode.evaluateXPath("//DIV[@class='list']/DIV/DIV/DIV/SPAN");
            } catch (XPatherException e) {
                log.error("Xpath problem", e);
                throw new ITwitterException("Xpath problem", e);
            }
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


}
