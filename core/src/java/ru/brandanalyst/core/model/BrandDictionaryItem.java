package ru.brandanalyst.core.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/2/11
 * Time: 9:45 PM
 */
public class BrandDictionaryItem {
    private final String term;
    private final long termId;
    private List<String> items;

    public BrandDictionaryItem(String term, long termId) {
        this.term = term;
        this.termId = termId;
        items = new ArrayList<String>();
    }

    public void addItem(String item) {
        items.add(item);
    }

    public List<String> getItems() {
        return items;
    }
}
