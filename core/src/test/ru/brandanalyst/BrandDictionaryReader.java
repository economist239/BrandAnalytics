package ru.brandanalyst;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.io.IOUtils;
import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.BrandDictionaryItem;
import ru.brandanalyst.core.util.test.AbstractTest;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author Vanslov Evgeny (evans@yandex-team.ru)
 */
public class BrandDictionaryReader extends AbstractTest {
    private String configLocations[] = {"test-config.xml"};

    protected ProvidersHandler providersHandler;
    protected String file;

    private static class XmlBrandDictionaryItem {
        public String brandName;
        public Set<String> aliases = new HashSet<String>();
    }

    private BrandDictionaryItem toBrandDictionaryItem(XmlBrandDictionaryItem xmlItem) {
        Brand brand = providersHandler.getBrandProvider().getBrandByName(xmlItem.brandName);
        BrandDictionaryItem item = new BrandDictionaryItem(brand.getId());
        for (String alias : xmlItem.aliases) {
            item.addItem(alias);
        }
        return item;
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }

    private XStream generateXStream() {
        XStream xStream = new XStream();
        xStream.alias("item", XmlBrandDictionaryItem.class);
        return xStream;
    }

    private List<XmlBrandDictionaryItem> readXmlItems(String content) {
        XStream xStream = generateXStream();
        return (List<XmlBrandDictionaryItem>) xStream.fromXML(content);
    }

    public List<BrandDictionaryItem> read(String content) {
        List<BrandDictionaryItem> items = new ArrayList<BrandDictionaryItem>();
        for (XmlBrandDictionaryItem item : readXmlItems(content)) {
            items.add(toBrandDictionaryItem(item));
        }
        return items;
    }

    public void testReadXmlBrandItem() throws IOException {
        List<XmlBrandDictionaryItem> items = readXmlItems(IOUtils.toString(new FileReader(file)));
        for (XmlBrandDictionaryItem item : items) {
            System.out.println(item.aliases);
        }
    }

    public void testReadBrandItem() throws IOException {
        List<BrandDictionaryItem> items = read(IOUtils.toString(new FileReader(file)));
        for (BrandDictionaryItem item : items) {
            System.out.println(item.getItems());
        }
    }

    // rename to testWriteBrandItem
    public void testWriteBrandItem() throws IOException {
        XmlBrandDictionaryItem brandDictionaryItem = new XmlBrandDictionaryItem();
        brandDictionaryItem.brandName = "google";
        brandDictionaryItem.aliases.add("google");
        brandDictionaryItem.aliases.add("gogle");
        brandDictionaryItem.aliases.add("гугл");

        XStream xStream = generateXStream();
        String content = xStream.toXML(new ArrayList<XmlBrandDictionaryItem>(Arrays.asList(brandDictionaryItem)));

        FileWriter output = new FileWriter("a.xml");
        IOUtils.write(content, output);
        output.close();
    }
}

// brandName - unique in db
