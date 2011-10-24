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
 * Date: 19.10.11
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class YandexNewsScraperRuntimeListener implements ScraperRuntimeListener {
    protected SimpleJdbcTemplate jdbcTemplate;
    protected ArticleProvider articleProvider;
    YandexNewsScraperRuntimeListener(SimpleJdbcTemplate jdbcTemplate){
        setJdbcTemplate(jdbcTemplate);
    }
    protected void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    private Timestamp evalTimestamp(String stringDate){
        int nano =0;
        int second = 0;
        int minute = Integer.parseInt(stringDate.substring(20,22));
        int hour = Integer.parseInt(stringDate.substring(17,19));
        int day = Integer.parseInt(stringDate.substring(5,7));
        int year = Integer.parseInt(stringDate.substring(12,16));
        int month;
        switch(stringDate.substring(8,11)){
            case "Jan": month = 1; break;
            case "Feb": month = 2; break;
            case "Mar": month = 3; break;
            case "Apr": month = 4; break;
            case "May": month = 5; break;
            case "Jun": month = 6; break;
            case "Jul": month = 7; break;
            case "Aug": month = 8; break;
            case "Sep": month = 9; break;
            case "Oct": month = 10; break;
            case "Nov": month = 11; break;
            case "Dec": month = 12; break;
            default: month =0;
        }
        Timestamp timestamp = new Timestamp(year,month,day,hour,minute,second,nano);
        return timestamp;
    }
    public void onExecutionStart(Scraper scraper) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onExecutionPaused(Scraper scraper) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onExecutionContinued(Scraper scraper) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onNewProcessorExecution(Scraper scraper, BaseProcessor baseProcessor) {
/*
        if("empty".equalsIgnoreCase(scraper.getRunningProcessor().getElementDef().getShortElementName())){
            Variable newsTitle = (Variable)scraper.getContext().get("newsTitle");
            Variable newsText = (Variable)scraper.getContext().get("newsFullText");
            Variable newsLink = (Variable)scraper.getContext().get("newsURL");
            Variable newsDate = (Variable)scraper.getContext().get("newsDate");
            Variable temp = scraper.getContext().getVar("newsFullText");
            System.out.println(newsTitle.toString()+" "+newsText.toString()+" "+newsLink.toString()+" "+newsDate.toString()+" "+ temp.toString());
        }
*/
    }

    public void onExecutionEnd(Scraper scraper) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onProcessorExecutionFinished(Scraper scraper, BaseProcessor baseProcessor, Map map) {
        if("body".equalsIgnoreCase(baseProcessor.getElementDef().getShortElementName())){
            Variable newsTitle = (Variable)scraper.getContext().get("newsTitle");
            Variable newsText = (Variable)scraper.getContext().get("newsFullText");
            Variable newsLink = (Variable)scraper.getContext().get("newsURL");
            Variable newsDate = (Variable)scraper.getContext().get("newsDate");
            //String temp = new String(scraper.getContext().getVar("newsTitle").toString()) ;
            //System.out.println(newsTitle.toString()+" "+newsText.toString()+" "+newsLink.toString()+" "+newsDate.toString());
            String articleContent = newsText.toString().replaceAll("\\<.*?\\>","");;
            Timestamp articleTimestamp = evalTimestamp(newsDate.toString());
            String articleTitle = newsTitle.toString();
            String articleLink = newsLink.toString();
            Article article = new Article(1,1,1,articleTitle,articleContent,articleLink,articleTimestamp,0);
            //TODO: insert article into DB (how?)
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onExecutionError(Scraper scraper, Exception e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
