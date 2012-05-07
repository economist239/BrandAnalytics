package ru.brandanalyst.core.util;

/**
 * User: daddy-bear
 * Date: 30.04.12
 * Time: 13:43
 */
public class SimpleTextCleaner {

    private SimpleTextCleaner() {
    }

    public static String clean(final String input) {
        return cleanQuot(cleanHtml(input));
    }

    public static String cleanHtml(final String input) {
        return input.replaceAll("<[^>]+>", "");
    }

    public static String cleanQuot(final String input) {
        return input.replaceAll("\\&quot;", "\"");
    }
}
