package ru.brandanalyst.miner.processor;

import org.jetbrains.annotations.Nullable;
import ru.brandanalyst.core.model.Article;

/**
 * User: daddy-bear
 * Date: 29.04.12
 * Time: 20:48
 */
public class ArticleContentPostProcessor extends ArticlePostProcessor {

    @Nullable
    @Override
    public Article process(Article entity) {
        if (entity == null) {
            return null;
        }
        if (entity.getContent() != null && entity.getTitle() != null && !(entity.getContent().isEmpty()) && !(entity.getTitle().isEmpty())) {

            //TODO

            return entity;
        }
        return null;
    }
}
