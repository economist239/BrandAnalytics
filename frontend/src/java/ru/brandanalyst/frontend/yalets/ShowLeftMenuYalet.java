package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

public class ShowLeftMenuYalet extends AbstractYalet {

    public void process(InternalRequest req, InternalResponse res) {
//	String str = req.getParameter("id");
////	if (str != null) {
//	    res.add(manager.getBrand(Integer.parseInt(str)));
//	    return;
//	}

//        res.add(manager.getAllBrands());
	res.add(manager.getAllBrands());
    }
}
