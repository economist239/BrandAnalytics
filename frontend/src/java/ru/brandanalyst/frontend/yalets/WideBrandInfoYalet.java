package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;
import net.sf.xfresh.db.AbstractDbYalet;
import ru.brandanalyst.frontend.services.WideBrandInfoManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/19/11
 * Time: 7:48 PM
 */
public class WideBrandInfoYalet extends AbstractDbYalet {

    public void process(InternalRequest req, InternalResponse res) {

        long brandId = req.getLongParameter("id");

        WideBrandInfoManager manager = new WideBrandInfoManager(jdbcTemplate);

        if(manager.getBrand(brandId) != null) {
            res.add(manager.getArticlesForBrand(brandId));
            res.add(manager.getBrand(brandId));
            res.add(manager.getGraphsForBrand(brandId));
        } else {
            Xmler.Tag ans = Xmler.tag("error", "Brand not found. id=" + Long.toString(brandId));
            res.add(ans);
        }
    }
}
