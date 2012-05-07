package ru.brandanalyst.db;

import ru.brandanalyst.core.db.provider.interfaces.SemanticDictionaryProvider;
import ru.brandanalyst.core.model.SemanticDictionaryItem;

import java.util.HashSet;
import java.util.Set;

/**
 * User: dima
 * Date: 2/26/12
 * Time: 6:39 PM
 */
public class InMemorySemanticDictionaryProvider implements SemanticDictionaryProvider {
    Set<SemanticDictionaryItem> depot = new HashSet<SemanticDictionaryItem>();

    @Override
    public void setSemanticDictionaryItem(SemanticDictionaryItem item) {
        depot.add(item);
    }

    @Override
    public Set<SemanticDictionaryItem> getSemanticDictionary() {
        return depot;
    }
}
