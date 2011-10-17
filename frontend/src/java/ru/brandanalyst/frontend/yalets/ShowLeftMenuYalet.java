package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.xml.Xmler;
import net.sf.xfresh.db.AbstractDbYalet;
import ru.brandanalyst.frontend.services.LeftMenuManager;
import ru.brandanalyst.frontend.models.SimplyBrandForWeb;
import ru.brandanalyst.frontend.services.LeftMenuManager;

import java.util.List;

public class ShowLeftMenuYalet extends AbstractDbYalet {

    public void process(InternalRequest req, InternalResponse res) {

        LeftMenuManager manager = new LeftMenuManager();

        if(manager.getSearchResultByBrand(jdbcTemplate) != null) {
            res.add(manager.getSearchResultByBrand(jdbcTemplate));
        } else {
            Xmler.Tag ans = Xmler.tag("error", "Трололо");
            res.add(ans);
        }
    }
}
