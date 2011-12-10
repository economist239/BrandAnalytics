package ru.brandanalyst.core.db.provider.global;

import ru.brandanalyst.core.model.SemanticDictionaryItem;

import java.util.HashSet;

public interface SemanticDictinaryProvider {
    @Deprecated
    public void cleanDataStore();

    public HashSet<SemanticDictionaryItem> getSemanticDictionary();

    public void setSemanticDictionaryItem(SemanticDictionaryItem item);
}
