package ru.brandanalyst.core.db.provider.interfaces;

import ru.brandanalyst.core.model.SemanticDictionaryItem;

import java.util.HashSet;

public interface SemanticDictionaryProvider {
    @Deprecated
    public void cleanDataStore();

    public HashSet<SemanticDictionaryItem> getSemanticDictionary();

    public void setSemanticDictionaryItem(SemanticDictionaryItem item);
}
