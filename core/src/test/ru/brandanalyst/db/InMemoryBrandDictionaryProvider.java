package ru.brandanalyst.db;

import ru.brandanalyst.core.db.provider.interfaces.BrandDictionaryProvider;
import ru.brandanalyst.core.model.BrandDictionaryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dima
 * Date: 2/26/12
 * Time: 6:34 PM
 */
public class InMemoryBrandDictionaryProvider implements BrandDictionaryProvider {
    @Override
    public BrandDictionaryItem getDictionaryItem(long brandId) {
        return new BrandDictionaryItem(brandId);
    }

    @Override
    public List<BrandDictionaryItem> getDictionary() {
        return new ArrayList<BrandDictionaryItem>();
    }
}
