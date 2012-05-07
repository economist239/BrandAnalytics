package ru.brandanalyst.miner.quote;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import ru.brandanalyst.core.db.provider.interfaces.BrandProvider;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.db.provider.interfaces.TickerProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.model.SingleDot;
import ru.brandanalyst.miner.AbstractGrabberTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author OlegPan
 *         This class grabbs information about quotes
 */
public class GrabberTaskFinam extends AbstractGrabberTask {
    private static final Logger log = Logger.getLogger(GrabberTaskFinam.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    protected GraphProvider graphProvider;
    protected BrandProvider brandProvider;
    protected TickerProvider tickerProvider;

    private final static long TICKER_ID = 5;

    protected void grab() {
        log.info("finam grabber started");
        tickerProvider = handler.getTickerProvider();
        brandProvider = handler.getBrandProvider();
        graphProvider = handler.getGraphProvider();

        final LocalDate currentDate = new LocalDate(new Date());

        for (final Brand b : brandProvider.getAllBrands()) {

            final String finamUrl = b.getParams().getRawParams();
            if (finamUrl.isEmpty()) continue;
            log.info("parsing quotes for brand " + b.getName());

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(new URL(finamUrl).openStream()));
                in.readLine();

                Graph graph = new Graph();

                while (in.ready()) {
                    try {

                        final String oneQuote = in.readLine();
                        final String[] tokens = oneQuote.split(",");

                        final LocalDate quoteDate = new LocalDate(DATE_FORMAT.parse(tokens[2]).getTime());

                        if (quoteDate.compareTo(currentDate) >= 0) {
                            graph.addPoint(new SingleDot(quoteDate, (Double.parseDouble(tokens[7]))));
                        }

                    } catch (ParseException ignored) {

                    }
                }
                graphProvider.writeGraph(graph, b.getId(), TICKER_ID);
                log.info("brand processed");

            } catch (IOException e) {
                log.error("Error on getting quote for " + finamUrl, e);
            }
        }

    }

}
