package ru.brandanalyst.miner.quote;

import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import ru.brandanalyst.core.db.provider.interfaces.BrandProvider;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.db.provider.interfaces.TickerProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.model.SingleDot;
import ru.brandanalyst.core.model.TickerPair;
import ru.brandanalyst.core.util.cortege.Pair;
import ru.brandanalyst.miner.grabbers.Grabber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author OlegPan
 *         This class grabbs information about quotes
 */
public class GrabberFinam extends Grabber {
    private static final Logger log = Logger.getLogger(GrabberFinam.class);

    protected GraphProvider graphProvider;
    protected BrandProvider brandProvider;
    protected TickerProvider tickerProvider;
    private final static String SOURCE_URL = "http://www.finam.ru/analysis/export/default.asp";
    private final static String TICKER_NAME = "котировки";

    public void grab(Date date) {
        tickerProvider = handler.getTickerProvider();
        brandProvider = handler.getBrandProvider();
        graphProvider = handler.getGraphProvider();
    }

    private void grab(Integer beginDay, Integer beginMonth, Integer beginYear) {
        List<Brand> brands = brandProvider.getAllBrands();
        for (Brand b : brands) {
            String finamName = b.getFinamName();
            if (finamName.isEmpty()) continue;
            log.info("Getting quote for " + finamName);
            try {
                WebClient webClient = new WebClient();
                HtmlPage page = (HtmlPage) webClient.getPage(SOURCE_URL);
                HtmlForm form = page.getFormByName("chartform");
                HtmlSelect period = form.getSelectByName("p");
                period.setSelectedAttribute("8", true);
                HtmlSelect brandChoose = form.getSelectByName("em");
                brandChoose.setSelectedAttribute(brandChoose.getOptionByText(finamName).getValueAttribute(), true);
                HtmlSelect month = form.getSelectByName("mf");
                month.setSelectedAttribute(beginMonth.toString(), true);
                HtmlInput day = form.getInputByName("df");
                day.setValueAttribute(beginDay.toString());
                HtmlInput year = form.getInputByName("yf");
                year.setValueAttribute(beginYear.toString());
                HtmlSubmitInput submit = (HtmlSubmitInput) form.getInputByValue("Получить файл");
                UnexpectedPage uPage = (UnexpectedPage) submit.click();
                BufferedReader br = new BufferedReader(new InputStreamReader(uPage.getInputStream()));
                List<TickerPair> tickers = tickerProvider.getTickers();
                long tickerId = -1;
                for (TickerPair tp : tickers)
                    if (tp.getName().equals(TICKER_NAME)) {
                        tickerId = tp.getId();
                        break;
                    }
                Graph graph = new Graph(TICKER_NAME);
                String oneQuote;
                while ((oneQuote = br.readLine()) != null) {
                    if (oneQuote.startsWith("<")) continue;
                    StringTokenizer st = new StringTokenizer(oneQuote, ",");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                    st.nextToken();
                    st.nextToken();
                    Date date;
                    try {
                        date = dateFormat.parse(st.nextToken());
                    } catch (ParseException e) {
                        date = new Date();
                    }
                    st.nextToken();
                    st.nextToken();
                    graph.addPoint(new SingleDot(new Timestamp(date.getTime()), (Double.parseDouble(st.nextToken()) + Double.parseDouble(st.nextToken())) / 2));
                }
                graphProvider.writeGraph(graph, b.getId(), tickerId);
                webClient.closeAllWindows();
            } catch (IOException e) {
                log.error("Error on getting quote for !!!" + finamName, e);
            }
        }

    }

    Pair<String, String> namePair(String finamName) {
        try {
            JSONArray a = new JSONArray(finamName);
            return Pair.of(a.get(0).toString(), a.get(1).toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
