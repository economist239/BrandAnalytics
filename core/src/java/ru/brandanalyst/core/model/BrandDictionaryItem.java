package ru.brandanalyst.core.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/2/11
 * Time: 9:45 PM
 */
public class BrandDictionaryItem {
    private final long brandId;
    private Set<String> items;

    public BrandDictionaryItem(long brandId) {
        this.brandId = brandId;
        items = new HashSet<String>();
    }

    public void addItem(String item) {
        items.add(item);
    }

    public Set<String> getItems() {
        return items;
    }

    public long getBrandId() {
        return brandId;
    }

}
