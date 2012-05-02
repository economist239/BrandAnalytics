package ru.brandanalyst.core.util;

import org.apache.log4j.Logger;
import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

/**
 * Alexandra Mikhaylova mikhaylova@yandex-team.ru
 */
public class ArticleCleaner {
    private static final Logger log = Logger.getLogger(ArticleCleaner.class);
    private static final BoilerpipeExtractor extractor = new ArticleExtractor();

    public static String cleanArticle(final String article) {
        try {
            return extractor.getText(article);
        } catch (BoilerpipeProcessingException e) {
            log.error("Error: " + e.toString() + "! Article has not been cleaned!");
            return article;
        }
    }
}