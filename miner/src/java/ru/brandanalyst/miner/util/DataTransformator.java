package ru.brandanalyst.miner.util;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/24/11
 * Time: 6:20 PM
 */
public final class DataTransformator {

    private DataTransformator() {

    }

    public static String clearString(String toClear) {
        int a, b;
        while (toClear.indexOf('<') != (-1)) {
            a = toClear.indexOf('<');
            b = toClear.indexOf('>');
            if (b > a) {
                toClear = toClear.substring(0, a) + toClear.substring(b, toClear.length());
                toClear = toClear.replaceFirst(">", " ");
            }
        }
        toClear = toClear.replaceAll("\t", "");
        toClear = toClear.replaceAll(" {2,}", " ");
        toClear = toClear.replaceAll(" {1,}[.]", ".");
        toClear = toClear.replaceAll(" {1,}[,]", ",");
        return toClear;
    }
}
