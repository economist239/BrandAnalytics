package ru.brandanalyst.miner.quote;

import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.mysql.MySQLBrandProvider;
import ru.brandanalyst.core.db.provider.mysql.MySQLGraphProvider;
import ru.brandanalyst.core.db.provider.mysql.MySQLTickerProvider;
import ru.brandanalyst.core.db.provider.mysql.MySQLTickerProvider.TickerPair;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.model.SingleDot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author OlegPan
 * This class grabbs information about quotes
 */
public class GrabberFinam {
    private static final Logger log = Logger.getLogger(GrabberFinam.class);
    protected SimpleJdbcTemplate jdbcTemplate;
    protected MySQLGraphProvider graphProvider;
    protected MySQLBrandProvider brandProvider;
    protected MySQLTickerProvider tickerProvider;
    private final String sourceURL="http://www.finam.ru/analysis/export/default.asp";
    private final String tickerName="котировки";
    public GrabberFinam(SimpleJdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
        graphProvider= new MySQLGraphProvider(jdbcTemplate);
        brandProvider= new MySQLBrandProvider(jdbcTemplate);
        tickerProvider= new MySQLTickerProvider(jdbcTemplate);
    }

    public void grab(Integer beginDay,Integer beginMonth, Integer beginYear)
    {
               List<Brand> brands=brandProvider.getAllBrands();
               for (Brand b:brands)
               {
                   String finamName=b.getFinamName();
                   if (finamName.isEmpty()) continue;
                   log.info("Getting quote for "+finamName);
                   try
                   {
                        WebClient webClient = new WebClient();
                        HtmlPage page = (HtmlPage) webClient.getPage(sourceURL);
                        HtmlForm form = page.getFormByName("chartform");
                        HtmlSelect period=form.getSelectByName("p");
                        period.setSelectedAttribute("8",true);
                        HtmlSelect brandChoose=form.getSelectByName("em");
                        brandChoose.setSelectedAttribute(brandChoose.getOptionByText(finamName).getValueAttribute(), true);
                        HtmlSelect month=form.getSelectByName("mf");
                        month.setSelectedAttribute(beginMonth.toString(),true);
                        HtmlInput day=form.getInputByName("df");
                        day.setValueAttribute(beginDay.toString());
                        HtmlInput year=form.getInputByName("yf");
                        year.setValueAttribute(beginYear.toString());
                        HtmlSubmitInput submit = (HtmlSubmitInput) form.getInputByValue("Получить файл");
                        UnexpectedPage uPage= (UnexpectedPage) submit.click();
                        BufferedReader br=new BufferedReader(new InputStreamReader(uPage.getInputStream()));
                        List<TickerPair> tickers= tickerProvider.getTickers();
                        long tickerId=-1;
                        for (TickerPair tp:tickers)
                            if (tp.getName().equals(tickerName))
                            {
                                tickerId=tp.getId();
                                break;
                            }
                        Graph graph=new Graph(tickerName);
                        String oneQuote;
                        while ((oneQuote=br.readLine())!=null)
                        {
                            if (oneQuote.startsWith("<")) continue;
                            StringTokenizer st=new StringTokenizer(oneQuote,",");
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
                        graph.addPoint(new SingleDot(new Timestamp(date.getTime()),(Double.parseDouble(st.nextToken())+Double.parseDouble(st.nextToken()))/2));
                        }
                        graphProvider.writeGraph(graph,b.getId(),tickerId);

                   }
                   catch (Exception e)
                   {
                       log.error("Error on getting quote for !!!"+finamName,e);
                   }
               }

    }
}
