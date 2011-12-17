package ru.brandanalyst.core.db.provider.interfaces;

import ru.brandanalyst.core.model.SemanticDictionaryItem;

import java.util.HashSet;
import java.util.Set;

public interface SemanticDictionaryProvider {
    @Deprecated
    public void cleanDataStore();

    public Set<SemanticDictionaryItem> getSemanticDictionary();

    public void setSemanticDictionaryItem(SemanticDictionaryItem item);
}
