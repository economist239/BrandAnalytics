package ru.brandanalyst.core.xfresh;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.xml.sax.ContentHandler;
import ru.brandanalyst.core.util.Jsonable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Vanslov Evgeny
 * Created: 1/22/12 5:12 PM
 */
public class DefaultContentHandlerGenerator implements ContentHandlerGenerator {


    @Override
    public ContentHandler generate(OutputStream outputStream, List data) {
        if (!data.isEmpty() && data.get(0) instanceof Jsonable) {
            return new JsonSerializer(outputStream);
        }
        return new MyXMLSerializer(outputStream);
    }

    private static class MyXMLSerializer extends XMLSerializer {
        private static final String DEFAULT_ENCODING = "utf-8";
        private static final OutputFormat DEFAULT_FORMAT = new OutputFormat("XML", DEFAULT_ENCODING, false);

        private static final String XML_STYLESHEET_PI = "xml-stylesheet";

        private MyXMLSerializer(final OutputStream writer) {
            super(writer, DEFAULT_FORMAT);
        }

        @Override
        public void processingInstructionIO(final String target, final String code) throws IOException {
            if (!XML_STYLESHEET_PI.equalsIgnoreCase(target)) {
                super.processingInstructionIO(target, code);
            }
        }
    }
}
