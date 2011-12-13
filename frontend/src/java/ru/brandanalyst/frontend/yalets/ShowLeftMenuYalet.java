package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;
import ru.brandanalyst.core.model.simple.SimplyBrandForWeb;
import ru.brandanalyst.frontend.services.LeftMenuManager;

import java.util.List;

/**
 * Ялет, показывающий левое меню
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/25/11
 * Time: 11:40 PM
 */
public class ShowLeftMenuYalet extends AbstractDbYalet {

    public void process(InternalRequest req, InternalResponse res) {

        LeftMenuManager manager = new LeftMenuManager(providersHandler);

        List<SimplyBrandForWeb> brandList = manager.getSearchResultByBrand();
        if (brandList != null) {
            res.add(brandList);
        } else {
            Xmler.Tag ans = Xmler.tag("error", "Трололо");
            res.add(ans);
        }
    }
}
