package ru.brandanalyst.miner.util;

/**
 * @author OlegPan
 */
public class LentaDataTransformator {
     public static String clearNewsString(String source,String link) {
        link=link.replace("//", "");
         try
         {
            link=link.substring(link.indexOf("/"))+" -->";
            source=source.substring(source.indexOf(link));
            source=source.substring(source.indexOf(">")+1,source.indexOf("<p class")).trim();
         }
         catch (Exception e)
         {
             return "";
         }
         int a,b;
        while(source.indexOf('<') != (-1)){
            a = source.indexOf('<');
            b = source.indexOf('>');
            if( b > a ){
                   source = source.substring(0,a) + source.substring(b,source.length());
                   source = source.replaceFirst(">", " ");
            }
        }
         source = source.replaceAll("\n", " ");
        source = source.replaceAll(" {2,}", " ");
        source = source.replaceAll(" {1,}[.]", ".");
        source = source.replaceAll(" {1,}[,]", ",");
        return source;
    }
    public static String clearArticlesString(String source){
        try
        {
            source=source.substring(source.indexOf("<p class=\"first\">")+17);
            if (!source.contains("<div class=\"author\"")) source=source.substring(0,source.indexOf("<p class=")).trim();
            else source=source.substring(0,source.indexOf("<div class=\"author\"")).trim();
        }
        catch (Exception e)
         {
             return "";
         }
        int a,b;
        while(source.indexOf('<') != (-1)){
            a = source.indexOf('<');
            b = source.indexOf('>');
            if( b > a ){
                   source = source.substring(0,a) + source.substring(b,source.length());
                   source = source.replaceFirst(">", " ");
            }
        }
         source = source.replaceAll("\n", " ");
        source = source.replaceAll(" {2,}", " ");
        source = source.replaceAll(" {1,}[.]", ".");
        source = source.replaceAll(" {1,}[,]", ",");
        return source;
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
        toClear = toClear.replaceAll("\n"," ");
        toClear = toClear.replaceAll("\t", "");
        toClear = toClear.replaceAll(" {2,}", " ");
        toClear = toClear.replaceAll(" {1,}[.]", ".");
        toClear = toClear.replaceAll(" {1,}[,]", ",");
        toClear = toClear.replaceAll("  "," ");
        return toClear;
    }

}
