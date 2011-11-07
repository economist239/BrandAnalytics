package ru.brandanalyst.miner.util;

import ru.brandanalyst.core.model.BrandDictionaryItem;

import java.util.ArrayList;
import java.util.List;

public class StringChecker {
    public static List<Long> hasTerm(List<BrandDictionaryItem> brandItems, String title) {
        List<Long> result=new ArrayList<Long>();
        boolean haveTerm=false;
        for (BrandDictionaryItem brandItem : brandItems)
            for (String item:brandItem.getItems())
                if (title.toLowerCase().contains(item.toLowerCase())) result.add(brandItem.getBrandId());
        return result;
    }
}
