package ru.brandanalyst.miner.util;

import ru.brandanalyst.core.model.BrandDictionaryItem;

import java.util.LinkedList;
import java.util.List;

public class StringChecker {

    private StringChecker() {
    }

    public static List<Long> hasTerm(List<BrandDictionaryItem> brandItems, String title) { //O(nl^2)
        List<Long> result = new LinkedList<Long>();

        for (BrandDictionaryItem brandItem : brandItems) {
            if (hasTerm(brandItem, title)) {
                result.add(brandItem.getBrandId());
            }
        }
        return result;
    }

    public static boolean hasTerm(BrandDictionaryItem brandItem, String title) { //O(l^2 )
        title = title.toLowerCase();
//        for (String item : brandItem.getItems())
//         if (title.toLowerCase().contains(item.toLowerCase())) return true;
//        return false;
        for (int i = 0; i < title.length(); i++) {
            for (int j = i + 1; j < title.length(); j++) {
                if (brandItem.getItems().contains(title.substring(i, j))) {
                    return true;
                }
            }
        }
        return false;
    }
}
