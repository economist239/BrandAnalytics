package ru.brandanalyst.miner.twitter;

import org.apache.log4j.Logger;
import ru.brandanalyst.core.db.provider.interfaces.ArticleProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.miner.AbstractGrabberTask;
import twitter4j.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Александр Сенов
 * Date: 10.10.11
 * Time: 14:14
 */
public class GrabberTaskTwitter extends AbstractGrabberTask {
    private static final Logger log = Logger.getLogger(GrabberTaskTwitter.class);

    public void grab() {
        grab(new Date());
    }

    public void grab(Date timeLimit) {
        log.info("ITwitter grabber started...");
        Twitter twitter = new TwitterFactory().getInstance();

        List<Brand> brandList = handler.getBrandProvider().getAllBrands();
        ArticleProvider articleProvider = handler.getArticleProvider();

        for (Brand b : brandList) {

        }
        log.info("twitter grabber finished succesful.");
    }

    private static Timestamp getSimpleTime(Date date) {
        return new Timestamp((new Date(date.getYear(), date.getMonth(), date.getDay())).getTime());
    }
}
