package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.db.AbstractDbYalet;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.db.provider.BrandProvider;

public class ShowLeftMenuYalet extends AbstractDbYalet {

    public void process(InternalRequest req, InternalResponse res) {
        //our frontend is only just for fan now=)
//       res.add(manager.getAllBrands());
       BrandProvider dataStore = new BrandProvider(jdbcTemplate);
 //       dataStore.cleanDataStore();
        Brand b1 = new Brand(1,"1","1","","");
        dataStore.writeBrandToDataStore(b1);
        b1 = new Brand(3,"3","3","","");
        dataStore.writeBrandToDataStore(b1);
        b1 = new Brand(2,"2","2","","");
        dataStore.writeBrandToDataStore(b1);
        res.add(dataStore.getBrandByName("1"));
//	res.add(manager.getAllBrands());
    }
}
