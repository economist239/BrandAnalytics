package ru.brandanalyst.frontend.yalets.cached;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.springframework.beans.factory.annotation.Required;
import ru.brandanalyst.core.model.Mention;
import ru.brandanalyst.core.util.Cf;
import ru.brandanalyst.frontend.yalets.AbstractDbYalet;

import java.util.Date;
import java.util.List;

/**
 * @author daddy-bear
 *         Date: 04.05.12 - 16:49
 */

public class CachedGetLatestMentionsYalet extends AbstractDbYalet {

    private long cachingTime;

    @Required
    public void setCachingTime(long cachingTime) {
        this.cachingTime = cachingTime;
    }

    private Date latestCalling = new Date(0L);

    private List<Mention> cachedMentions = Cf.newArrayList();

    @Override
    public void process(InternalRequest req, InternalResponse res) {
        if (new Date().getTime() - latestCalling.getTime() > cachingTime) {
            cachedMentions = providersHandler.getMentionProvider().getLatestMentions();
        }
        res.add(cachedMentions);
    }
}
