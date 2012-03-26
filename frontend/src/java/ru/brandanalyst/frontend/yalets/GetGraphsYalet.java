package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.SelfWriter;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.util.Jsonable;

import java.util.ArrayList;
import java.util.List;

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
        log.debug("Incoming");
        final long brandId = req.getLongParameter("brand");
        final String[] tickerIds = req.getParameters("ticker");
        List<Chart> charts = new ArrayList<Chart>();
        for(String tickerId : tickerIds){
            final Graph graph = providersHandler.getGraphProvider().getGraphByTickerAndBrand(brandId, Long.parseLong(tickerId));
            final Brand brand = providersHandler.getBrandProvider().getBrandById(brandId);
            charts.add(new Chart(graph, brand));
        }
        Charts c = new Charts(charts);
        res.add(c);
    }
    
    private static class Charts implements  Jsonable,SelfWriter{
        private List<Chart> charts;

        private Charts(List<Chart> charts) {
            this.charts = charts;
        }

        @Override
        public JSONObject asJson() {
            try {
                JSONArray array = new JSONArray();
                int i=0;
                for(Chart c : charts){
                    array.put(i++,c.asJson());
                }
                return new JSONObject().put("charts", array);
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
