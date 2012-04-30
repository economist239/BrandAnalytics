package ru.brandanalyst.db;

import ru.brandanalyst.core.db.provider.interfaces.BrandDictionaryProvider;
import ru.brandanalyst.core.db.provider.interfaces.BrandProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.BrandDictionaryItem;
import ru.brandanalyst.core.util.Cf;

import java.util.List;

/**
 * User: dima
 * Date: 2/26/12
 * Time: 6:34 PM
 */
public class InMemoryBrandDictionaryProvider implements BrandDictionaryProvider {
    BrandProvider brandProvider = new InMemoryBrandProvider();

    @Override
    public BrandDictionaryItem getDictionaryItem(long brandId) {
        BrandDictionaryItem item = new BrandDictionaryItem(brandId);
        item.addItem(brandProvider.getBrandById(brandId).getName().toLowerCase());
        System.out.println("item added: " + brandProvider.getBrandById(brandId).getName().toLowerCase());
        return item;
    }

    @Override
    public List<BrandDictionaryItem> getDictionary() {
        List<BrandDictionaryItem> items = Cf.newArrayList();
        for (Brand b : brandProvider.getAllBrands()) {
            items.add(getDictionaryItem(b.getId()));
        }
        return items;
    }
}
