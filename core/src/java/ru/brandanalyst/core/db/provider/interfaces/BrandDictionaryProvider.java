package ru.brandanalyst.core.db.provider.interfaces;


import ru.brandanalyst.core.model.BrandDictionaryItem;

import java.util.List;

public interface BrandDictionaryProvider {
    public BrandDictionaryItem getDictionaryItem(long brandId);

    public List<BrandDictionaryItem> getDictionary();
}
