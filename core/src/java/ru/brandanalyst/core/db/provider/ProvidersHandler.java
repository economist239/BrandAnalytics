package ru.brandanalyst.core.db.provider;

import org.springframework.beans.factory.annotation.Required;
import ru.brandanalyst.core.db.provider.interfaces.*;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/10/11
 * Time: 9:55 PM
 */
public class ProvidersHandler {

    public ArticleProvider getArticleProvider() {
        return articleProvider;
    }

    public BrandProvider getBrandProvider() {
        return brandProvider;
    }

    public BrandDictionaryProvider getBrandDictionaryProvider() {
        return brandDictionaryProvider;
    }

    public GraphProvider getGraphProvider() {
        return graphProvider;
    }

    public InformationSourceProvider getInformationSourceProvider() {
        return informationSourceProvider;
    }

    public SemanticDictionaryProvider getSemanticDictionaryProvider() {
        return semanticDictionaryProvider;
    }

    public TickerProvider getTickerProvider() {
        return tickerProvider;
    }

    public BranchesProvider getBranchesProvider() {
        return branchesProvider;
    }

    public MentionProvider getMentionProvider() {
        return mentionProvider;
    }

    @Required
    public void setArticleProvider(ArticleProvider articleProvider) {
        this.articleProvider = articleProvider;
    }

    @Required
    public void setBrandProvider(BrandProvider brandProvider) {
        this.brandProvider = brandProvider;
    }

    @Required
    public void setBrandDictionaryProvider(BrandDictionaryProvider brandDictionaryProvider) {
        this.brandDictionaryProvider = brandDictionaryProvider;
    }

    @Required
    public void setGraphProvider(GraphProvider graphProvider) {
        this.graphProvider = graphProvider;
    }

    @Required
    public void setInformationSourceProvider(InformationSourceProvider informationSourceProvider) {
        this.informationSourceProvider = informationSourceProvider;
    }

    @Required
    public void setSemanticDictionaryProvider(SemanticDictionaryProvider semanticDictionaryProvider) {
        this.semanticDictionaryProvider = semanticDictionaryProvider;
    }

    @Required
    public void setTickerProvider(TickerProvider tickerProvider) {
        this.tickerProvider = tickerProvider;
    }

    @Required
    public void setBranchesProvider(BranchesProvider branchesProvider) {
        this.branchesProvider = branchesProvider;
    }

    @Required
    public void setMentionProvider(MentionProvider mentionProvider) {
        this.mentionProvider = mentionProvider;
    }

    private ArticleProvider articleProvider;
    private BrandProvider brandProvider;
    private BrandDictionaryProvider brandDictionaryProvider;
    private GraphProvider graphProvider;
    private MentionProvider mentionProvider;
    private InformationSourceProvider informationSourceProvider;
    private SemanticDictionaryProvider semanticDictionaryProvider;
    private TickerProvider tickerProvider;
    private BranchesProvider branchesProvider;
}
