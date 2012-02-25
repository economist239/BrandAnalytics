package ru.brandanalyst.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlcleaner.*;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Vanslov Evgeny (evans@yandex-team.ru)
 */
public class XmlProvider {
    private static final Log log = LogFactory.getLog(XmlProvider.class);

    private static final HtmlCleaner cleaner;
    private static final CleanerProperties props;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; rv:12.0a2) Gecko/20120203 Firefox/12.0a2";

    static {
        cleaner = new HtmlCleaner();
        props = cleaner.getProperties();
        props.setAllowHtmlInsideAttributes(true);
        props.setAllowMultiWordAttributes(true);
        props.setRecognizeUnicodeChars(true);
        props.setOmitComments(true);
    }

    public static TagNode getTagNode(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", USER_AGENT);
        return new HtmlCleaner(props).clean(connection.getInputStream());
    }

    public static String getContent(URL url) throws IOException {
        return new SimpleXmlSerializer(props).getXmlAsString(getTagNode(url));
    }
}
