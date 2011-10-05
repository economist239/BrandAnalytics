package net.sf.xfresh.db;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * Date: Nov 10, 2010
 * Time: 9:29:52 PM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public abstract class AbstractDbYalet implements Yalet {
    protected SimpleJdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(final SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public abstract void process(InternalRequest req, InternalResponse res);
}
