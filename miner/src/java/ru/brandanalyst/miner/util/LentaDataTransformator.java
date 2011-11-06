package ru.brandanalyst.miner.util;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/6/11
 * Time: 8:39 PM
 */
public abstract class LentaDataTransformator {
    public static String clearNewsString(String source, String link) {
        link = link.replace("//", "");
        try {
            link = link.substring(link.indexOf("/")) + " -->";
            source = source.substring(source.indexOf(link));
            source = source.substring(source.indexOf(">") + 1, source.indexOf("<p class")).trim();
        } catch (Exception e) {
            return "";
        }

        source = DataTransformator.clearString(source);
        return source;
    }

    public static String clearArticlesString(String source) {
        try {
            source = source.substring(source.indexOf("<p class=\"first\">") + 17);
            if (!source.contains("<div class=\"author\""))
                source = source.substring(0, source.indexOf("<p class=")).trim();
            else source = source.substring(0, source.indexOf("<div class=\"author\"")).trim();
        } catch (Exception e) {
            return "";
        }

        source = DataTransformator.clearString(source);
        return source;
    }
}
