package ru.brandanalyst.core.db.provider.global;


import ru.brandanalyst.core.model.BrandDictionaryItem;

import java.util.List;

public interface GlobalBrandDictionaryProvider {
    @Deprecated
    public void cleanDataStore();

    public BrandDictionaryItem getDictionaryItem(long brandId);

    public List<BrandDictionaryItem> getDictionary();
}
