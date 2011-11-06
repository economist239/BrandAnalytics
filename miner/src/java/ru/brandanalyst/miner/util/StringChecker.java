package ru.brandanalyst.miner.util;

import java.util.List;

public class StringChecker {
    public static boolean hasTerm(List<String> items, String title) {
        for (String item : items)
            if (title.toLowerCase().contains(item.toLowerCase())) return true;
        return false;
    }
}
