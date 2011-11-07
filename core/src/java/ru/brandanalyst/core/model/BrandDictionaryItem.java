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
    private final String brand;
    private final long brandId;
    private List<String> items;

    public BrandDictionaryItem(String brand, long brandId) {
        this.brand = brand;
        this.brandId = brandId;
        items = new ArrayList<String>();
    }

    public void addItem(String item) {
        items.add(item);
    }

    public List<String> getItems() {
        return items;
    }

    public long getBrandId() {
        return brandId;
    }

}
