package ru.brandanalyst.miner;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperRuntimeListener;
import org.webharvest.runtime.processors.BaseProcessor;
import org.webharvest.runtime.variables.Variable;
import ru.brandanalyst.core.db.provider.ArticleProvider;
import ru.brandanalyst.core.model.Article;


import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Obus
 * Date: 23.10.11
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
public class RiaNewsScraperRuntimeListener implements ScraperRuntimeListener {
    protected SimpleJdbcTemplate jdbcTemplate;
    protected ArticleProvider articleProvider;
    RiaNewsScraperRuntimeListener(SimpleJdbcTemplate jdbcTemplate){
        setJdbcTemplate(jdbcTemplate);
    }
    protected void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    private Timestamp evalTimestamp(String stringDate){
        stringDate = stringDate.replace("\n","");
        stringDate = stringDate.replace(" ","");
        int nano =0;
        int second = 0;
        int minute = Integer.parseInt(stringDate.substring(13,15));
        int hour = Integer.parseInt(stringDate.substring(10,12));
        int day = Integer.parseInt(stringDate.substring(0,2));
        int month = Integer.parseInt(stringDate.substring(3,5));
        int year = Integer.parseInt(stringDate.substring(6,10));
        Timestamp timestamp = new Timestamp(year,month,day,hour,minute,second,nano);
        return timestamp;
    }
    public void onExecutionStart(Scraper scraper) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onExecutionPaused(Scraper scraper) {

    }

    public void onExecutionContinued(Scraper scraper) {

    }

    public void onNewProcessorExecution(Scraper scraper, BaseProcessor baseProcessor) {
        if("body".equalsIgnoreCase(baseProcessor.getElementDef().getShortElementName())){
            String oneNew = scraper.getContext().get("oneNew").toString();
            if(oneNew.substring(1,7).equals("resume")){
                scraper.addVariableToContext("isUnusualNewsCite",1);

            }
            else{
                scraper.addVariableToContext("isUnusualNewsCite",0);

            }
            //System.out.println(scraper.getContext().getVar("i").toString());
        }

    }

    public void onExecutionEnd(Scraper scraper) {

    }

    public void onProcessorExecutionFinished(Scraper scraper, BaseProcessor baseProcessor, Map map) {
        if("body".equalsIgnoreCase(baseProcessor.getElementDef().getShortElementName())){
            Variable newsTitle = (Variable)scraper.getContext().get("newsTitle");
            Variable newsText = (Variable)scraper.getContext().get("newsFullText");
            Variable newsDate = (Variable)scraper.getContext().get("newsDate");
            Timestamp articleTimestamp = evalTimestamp(newsDate.toString());
            //TODO: relpace regexp parsing by smthng serious
            String articleContent = newsText.toString().replaceAll("\\<.*?\\>","");
            String articleTitle = newsTitle.toString();
            String articleLink = scraper.getContext().get("riaAbsoluteURL").toString() + scraper.getContext().get("oneNew").toString();
            Article article = new Article(1,1,1,articleTitle,articleContent,articleLink,articleTimestamp,0);
            //TODO: insert article into DB (how?)

        }
        if("empty".equalsIgnoreCase(baseProcessor.getElementDef().getShortElementName())){
            String tempNewsDate = scraper.getContext().get("tempNewsDate").toString();
            if(tempNewsDate.replace(" ","").equals("")){
                scraper.addVariableToContext("isReallyUnusualNewsCite",1);
            }
            else{
                scraper.addVariableToContext("isReallyUnusualNewsCite",0);
            }
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onExecutionError(Scraper scraper, Exception e) {
        //To change body of implemented methods use File | Settings | File Templates.
        if(e!=null) {
        System.out.println(e.getCause());
        }
    }
}
