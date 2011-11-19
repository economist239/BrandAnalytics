package ru.brandanalyst.miner.quote;

import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.ArticleProvider;
import ru.brandanalyst.core.db.provider.BrandProvider;
import ru.brandanalyst.core.db.provider.GraphProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.model.SingleDot;

import javax.jnlp.IntegrationService;
import java.io.BufferedReader;
import java.io.InputStream;
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
    protected GraphProvider graphProvider;
    protected BrandProvider brandProvider;
    protected BrandProvider tickerProvider;
    private final String sourceURL="http://www.finam.ru/analysis/export/default.asp";
    public GrabberFinam(SimpleJdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
        graphProvider= new GraphProvider(jdbcTemplate);
        brandProvider= new BrandProvider(jdbcTemplate);
    }

    public void grab(Integer beginDay,Integer beginMonth)
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
                        HtmlSubmitInput submit = (HtmlSubmitInput) form.getInputByValue("Получить файл");
                        UnexpectedPage uPage= (UnexpectedPage) submit.click();
                        BufferedReader br=new BufferedReader(new InputStreamReader(uPage.getInputStream()));
                        Graph graph=new Graph("Цена Акций");
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
                        graphProvider.writeGraph(graph,b.getId(),1);

                   }
                   catch (Exception e)
                   {
                       log.error("Error on getting quote for !!!"+finamName,e);
                   }
               }

    }
}
