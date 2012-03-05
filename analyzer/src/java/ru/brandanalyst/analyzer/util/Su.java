package ru.brandanalyst.analyzer.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Alexandra Mikhaylova mikhaylova@yandex-team.ru
 *
 * String utils
 **/

public class Su {
    private static final char[] punctuation = {' ', '.', ',', '!', '?', ':', ';', '_', '/', '\\', '\'', '\"', '|', '`', '(', ')', '[', ']', '\n'};

    private static boolean isPunctuation(final char c) {
        for (final char p : punctuation) {
            if (c == p) {
                return true;
            }
        }
        return false;
    }

    public static List<String> splitOnPunctuation(final String s) {
        final List<String> result = new ArrayList<String>();
        String res = "";
        for (final char c : s.toCharArray()) {
            if (isPunctuation(c)) {
                if (!res.isEmpty()) {
                    result.add(res);
                }
                res = "";
                continue;
            }
            res = res.concat(String.valueOf(c));
        }
        if (!res.isEmpty()) {
            result.add(res);
        }
        return result;
    }

    public static List<String> allToLowerCase(final List<String> words) {
        final List<String> result = new ArrayList<String>();
        for (final String word : words) {
            result.add(word.toLowerCase());
        }
        return result;
    }
}
