package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;
import org.json.JSONArray;
import org.json.JSONException;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.util.Batch;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/15/11
 * Time: 9:11 AM
 */
public class GetGraphsYalet extends AbstractDbYalet {
    public void process(InternalRequest req, final InternalResponse res) {
        try {
            final long brandId = req.getLongParameter("brand-id");
            JSONArray graphsIds = new JSONArray(req.getParameter("graph-ids"));

            final GraphProvider provider = providersHandler.getGraphProvider();
            Batch<Long> batch = new Batch<Long>() {
                @Override
                public void handle(List<Long> longs) {
                    for (Graph g : provider.getGraphByTickerAndBrand(brandId, longs)) {
                        res.add(Xmler.tag("graph", g));
                    }
                }
            };

            for (int i = 0; i < graphsIds.length(); i++) {
                batch.submit(Long.parseLong(graphsIds.get(i).toString()));
            }
        } catch (JSONException e) {
            res.add("{error : \"error\"}");
            throw new RuntimeException("can't get graphs",e);
        }
    }
}
