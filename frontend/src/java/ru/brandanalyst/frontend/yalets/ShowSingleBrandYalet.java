package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

public class ShowSingleBrandYalet implements Yalet {

    public void process(InternalRequest req, InternalResponse res) {
	String str = req.getParameter("id");
        //not worked yet
////	if (str != null) {
//	    res.add(manager.getBrand(Integer.parseInt(str)));
//	    return;
//	}
//	DBConnector connector = new DBConnector();
//	int i =  Integer.parseInt(str);
	
//        res.add(manager.getAllBrands());
//	res.add(connector.getBrand(i));
    }
}
