package ru.brandanalyst.core.util;

/**
 * Класс, вырезающий из текста первое предложение.
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/6/11
 * Time: 5:35 PM
 */
public abstract class TextConverter {
    public static String firstPhrase(String text) {
        int p = text.indexOf(". ");
        return text.substring(0, p) + "...";
    }
}
