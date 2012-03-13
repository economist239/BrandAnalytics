package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.SelfWriter;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.util.Jsonable;

import static net.sf.xfresh.util.XmlUtil.text;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/15/11
 * Time: 9:11 AM
 */
public class GetGraphsYalet extends AbstractDbYalet {
    private static final Logger log = Logger.getLogger(GetGraphsYalet.class);

    public void process(final InternalRequest req, final InternalResponse res) {
        final long brandId = req.getLongParameter("brand");
        final long tickerId = req.getLongParameter("ticker");

        final Graph graph = providersHandler.getGraphProvider().getGraphByTickerAndBrand(brandId, tickerId);
        final Brand brand = providersHandler.getBrandProvider().getBrandById(brandId);
        Chart chart = new Chart(graph, brand);
        res.add(chart);
        log.debug(chart.asJson().toString());
    }
    private static class Chart implements Jsonable, SelfWriter{
        public final Graph graph;
        public final Brand brand;
        public Chart(final Graph graph,final Brand brand){
            this.graph = graph;
            this.brand = brand;
        }

        @Override
        public JSONObject asJson() {
            try {
                return new JSONObject().put("brand", brand.asJson()).put("chart", graph.asJson());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void writeTo(ContentHandler handler) {
            try {
                text(handler, asJson().toString());
            } catch (SAXException e) {
                log.error("Error json creating", e);
            }
        }
    }
}
