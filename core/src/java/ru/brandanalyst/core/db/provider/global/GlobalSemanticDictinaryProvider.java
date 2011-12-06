package ru.brandanalyst.core.db.provider.global;

import ru.brandanalyst.core.model.SemanticDictionaryItem;

import java.util.HashSet;

public interface GlobalSemanticDictinaryProvider {
    @Deprecated
    public void cleanDataStore();

    public HashSet<SemanticDictionaryItem> getSemanticDictionary();

    public void setSemanticDictionaryItem(SemanticDictionaryItem item);
}
