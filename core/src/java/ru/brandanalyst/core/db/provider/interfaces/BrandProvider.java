package ru.brandanalyst.core.db.provider.interfaces;

import ru.brandanalyst.core.model.Brand;

import java.util.List;

public interface BrandProvider {
    public void writeBrandToDataStore(Brand brand);

    public void writeListOfBrandsToDataStore(List<Brand> brands);

    public Brand getBrandByName(String name);

    public Brand getBrandById(long brandId);

    public List<Brand> getAllBrands();
}
