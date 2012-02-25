import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Vanslov Evgeny (evans@yandex-team.ru)
 */
public class XmlProvider {
    private static final Log log = LogFactory.getLog(XmlProvider.class);

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        Document doc = getDocument();
        /*final XPathFactory xPathFactory = XPathFactory.newInstance();
        final XPathExpression nodeXPath = xPathFactory.newXPath().compile("//DIV[@class='list']");
        final NodeList nodeList = (NodeList) nodeXPath.evaluate(doc, XPathConstants.NODESET);
        for(int i = 0; i < nodeList.getLength(); ++i){
            System.out.println(nodeList.item(i).getTextContent());
        }*/
    }

    public static Document getDocument() throws IOException, ParserConfigurationException, SAXException {
        String content = getContent();

        DocumentBuilderFactory dbf =
                DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(IOUtils.toInputStream(content));
    }

    private static String getContent() throws IOException {
        //URL url = new URL("http://topsy.com/s/google/tweet?offset=22&om=ca&page=3&window=d");
        URL url = new URL("http://google.com");

        Tidy tidy = new Tidy();
        StringWriter writer = new StringWriter();
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);
        tidy.parse(url.openStream(), writer);
        return writer.getBuffer().toString();
    }
}
