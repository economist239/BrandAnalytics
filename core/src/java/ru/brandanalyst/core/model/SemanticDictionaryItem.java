package ru.brandanalyst.core.model;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/4/11
 * Time: 9:17 AM
 */
public class SemanticDictionaryItem {
    private final String term;
    private final double semanticValue;

    public SemanticDictionaryItem(String term, double semanticValue) {
        this.term = term;
        this.semanticValue = semanticValue;
    }

    public String getTerm() {
        return term;
    }

    public double getSemanticValue() {
        return semanticValue;
    }
}
