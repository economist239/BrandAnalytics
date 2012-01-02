package ru.brandanalyst.core.db.provider.interfaces;

import ru.brandanalyst.core.model.SemanticDictionaryItem;

import java.util.Set;

public interface SemanticDictionaryProvider {
    public Set<SemanticDictionaryItem> getSemanticDictionary();

    public void setSemanticDictionaryItem(SemanticDictionaryItem item);
}
