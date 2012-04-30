package ru.brandanalyst.core.util;

import org.junit.Test;

/**
 * User: daddy-bear
 * Date: 30.04.12
 * Time: 13:43
 */
public class SimpleTextCleaner {

    private SimpleTextCleaner() {}

    public static String clean(final String input) {
        return input.replaceAll("<[^>]+>", "");
    }
}
