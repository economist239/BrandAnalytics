package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;
import net.sf.xfresh.db.AbstractDbYalet;
import ru.brandanalyst.frontend.services.WideBrandInfoManager;

/**
 * Ялет показывающий информацию о бренде
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/19/11
 * Time: 7:48 PM
 */
public class WideBrandInfoYalet extends AbstractDbYalet {

    public void process(InternalRequest req, InternalResponse res) {

        long brandId = req.getLongParameter("id");

        long tickerId = 1;
        if (req.getAllParameters().containsKey("ticker_id")) {
            tickerId = req.getLongParameter("ticker_id");
        }

        WideBrandInfoManager manager = new WideBrandInfoManager(jdbcTemplate);

        if (manager.getBrand(brandId) != null) {
            res.add(manager.getArticlesForBrand(brandId));
            res.add(manager.getBrand(brandId));
            res.add(manager.getTickers());
            res.add(manager.getGraphsForBrand(brandId, tickerId));

        } else {
            Xmler.Tag ans = Xmler.tag("error", "Brand not found. id=" + Long.toString(brandId));
            res.add(ans);
        }
    }
}
