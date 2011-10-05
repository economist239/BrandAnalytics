package ru.brandanalyst.frontend;

import ru.brandanalyst.frontend.model.Brand;
import ru.brandanalyst.frontend.model.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class DBConnector {
    List<Brand> data;

    public DBConnector() {
        data = new ArrayList<Brand>();
        for (int i = 0; i < 5; ++i) {
            Brand brand = new Brand();
            brand.setId(Integer.toString(i));
            brand.setName("BRAND" + i);
            brand.setAbout("ABOUT" + i);

            String dots = "[" + Integer.toString(i) + ", " + Integer.toString(i*i)+ ", " + Integer.toString(i-3)+ ", " + Integer.toString(i+1)+ ", " + Integer.toString(i*i*i)+ ", " + Integer.toString(i) + "]";
            String description = "Как дела у бренда " + Integer.toString(i);
            Graph gr = new Graph(dots, description);
            brand.setGraph(gr);
            data.add(brand);
        }	
    }

    public List<Brand> getAllBrands() {
        return data;
    }

    public List<Brand> getBrand(int i) {
    	List<Brand> singleBrand = new ArrayList<Brand>();
        singleBrand.add(data.get(i));
        return singleBrand;
    }    
}