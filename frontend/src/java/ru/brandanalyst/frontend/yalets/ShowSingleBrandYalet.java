package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import ru.brandanalyst.frontend.*;

public class ShowSingleBrandYalet extends AbstractYalet {

    public void process(InternalRequest req, InternalResponse res) {
	String str = req.getParameter("id");
////	if (str != null) {
//	    res.add(manager.getBrand(Integer.parseInt(str)));
//	    return;
//	}
	DBConnector connector = new DBConnector();
	int i =  Integer.parseInt(str);
	
//        res.add(manager.getAllBrands());
	res.add(connector.getBrand(i));
    }
}
