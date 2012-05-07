package ru.brandanalyst.core.db.provider.interfaces;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import ru.brandanalyst.core.model.Mention;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alexsen
 * Date: 23.02.12
 * Time: 20:42
 */
public interface MentionProvider {

    public List<Mention> getLatestMentions();

    public List<Mention> getMentionsFrom(LocalDate date);

}
