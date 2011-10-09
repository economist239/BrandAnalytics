package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.db.AbstractDbYalet;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.storage.DataStore;

public class ShowLeftMenuYalet extends AbstractDbYalet {

    public void process(InternalRequest req, InternalResponse res) {
//	String str = req.getParameter("id");
////	if (str != null) {
//	    res.add(manager.getBrand(Integer.parseInt(str)));
//	    return;
//	}

//        res.add(manager.getAllBrands());
        DataStore dataStore = new DataStore(jdbcTemplate);
        dataStore.cleanDataStore();
        Brand b1 = new Brand(1,"1","1");
        dataStore.writeBrandToDataStore(b1);
        b1 = new Brand(3,"3","3");
        dataStore.writeBrandToDataStore(b1);
        b1 = new Brand(2,"2","2");
        dataStore.writeBrandToDataStore(b1);
        res.add(dataStore.getBrandByName("1"));
//	res.add(manager.getAllBrands());
    }
}
