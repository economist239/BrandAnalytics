package ru.brandanalyst.db;

import org.joda.time.LocalDate;
import ru.brandanalyst.core.db.provider.interfaces.MentionProvider;
import ru.brandanalyst.core.model.Mention;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dima
 * Date: 2/26/12
 * Time: 6:36 PM
 */
public class InMemoryMentionProvider implements MentionProvider {
    @Override
    public List<Mention> getLatestMentions() {
        return new ArrayList<Mention>();
    }

    @Override
    public List<Mention> getMentionsFrom(LocalDate date){
        return new ArrayList<Mention>();
    }
}
