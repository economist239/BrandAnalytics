package ru.brandanalyst.core.xfresh;

import org.xml.sax.ContentHandler;

import java.io.OutputStream;
import java.util.List;

/**
 * Vanslov Evgeny
 * Created: 1/22/12 5:11 PM
 */
public interface ContentHandlerGenerator {
    ContentHandler generate(OutputStream outputStream, List data);
}
