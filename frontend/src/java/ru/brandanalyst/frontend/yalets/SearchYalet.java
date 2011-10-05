package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 04.10.11
 * Time: 3:45
 * To change this template use File | Settings | File Templates.
 */
public class SearchYalet extends AbstractYalet {

    public void process(InternalRequest req, InternalResponse res) {
	    String query = req.getParameter("query");
        Xmler.Tag ans = Xmler.tag("answer", "Убейтесь, ничего не работает. Query: " + query);
        res.add(ans);
    }

}
