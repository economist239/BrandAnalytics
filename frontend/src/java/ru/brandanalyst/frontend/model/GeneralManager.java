package ru.brandanalyst.frontend.model;

import com.sun.java.util.jar.pack.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GeneralManager {
    List<Brand> results;

    public GeneralManager() {
        results = new ArrayList<Brand>();
        for (int i = 0; i < 5; ++i) {
            Brand brand = new Brand();
            brand.setId(Integer.toString(i));
            brand.setName("BRAND" + i);
            brand.setAbout("ABOUT" + i);
            results.add(brand);
        }
    }

    public List<Brand> getAllBrands() {
        return results;
    }

}
