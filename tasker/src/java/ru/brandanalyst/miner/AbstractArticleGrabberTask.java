package ru.brandanalyst.miner;

import org.springframework.beans.factory.annotation.Required;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.util.Batch;
import ru.brandanalyst.miner.processor.ArticlePostProcessor;

import java.util.List;

/**
 * User: daddy-bear
 * Date: 29.04.12
 * Time: 20:43
 */
public abstract class AbstractArticleGrabberTask extends AbstractGrabberTask {

    private List<ArticlePostProcessor> postProcessors;

    @Required
    public void setPostProcessors(List<ArticlePostProcessor> postProcessors) {
        this.postProcessors = postProcessors;
    }

    protected void doGrab() {

    }

    @Override
    protected void grab() {
        doGrab();

    }

    private Article postProcessing(Article article) {
        for (ArticlePostProcessor pp: postProcessors) {
            article = pp.process(article);
        }
        return article;
    }
}
