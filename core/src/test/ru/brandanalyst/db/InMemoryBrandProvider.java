package ru.brandanalyst.db;

import ru.brandanalyst.core.db.provider.interfaces.BrandProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.Params;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dima
 * Date: 2/26/12
 * Time: 6:09 PM
 */
public class InMemoryBrandProvider implements BrandProvider {
    int key;
    List<Brand> depot = new ArrayList<Brand>();

    {
        depot.add(new Brand(1, "Apple", "1", "1", 1, Params.empty()));
        depot.add(new Brand(2, "Microsoft", "2", "2", 2, Params.empty()));
        depot.add(new Brand(3, "Газпром", "3", "3", 1, Params.empty()));
        depot.add(new Brand(4, "Уралсиб", "4", "4", 2, Params.empty()));
        depot.add(new Brand(5, "Burger King", "5", "5", 1, Params.empty()));
        key = 5;

    }

    @Override
    public void writeBrandToDataStore(Brand brand) {
        depot.add(new Brand(key++, brand.getName(), brand.getDescription(), brand.getWebsite(), brand.getBranchId(), brand.getParams()));
    }

    @Override
    public void writeListOfBrandsToDataStore(List<Brand> brands) {
        for (Brand b: brands) {
            writeBrandToDataStore(b);
        }
    }

    @Override
    public Brand getBrandByName(String name) {
        for (Brand b: depot) {
            if (b.getName().equals(name)) {
                return b;
            }
        }
        return depot.get(0);
    }

    @Override
    public Brand getBrandById(long brandId) {
        for (Brand b: depot) {
            if (b.getId() == brandId) {
                return b;
            }
        }
        return depot.get(0);
    }

    @Override
    public List<Brand> getAllBrands() {
        return new ArrayList<Brand>(depot);
    }
}
