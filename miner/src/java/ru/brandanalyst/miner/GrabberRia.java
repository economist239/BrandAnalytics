package ru.brandanalyst.miner;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Obus
 * Date: 23.10.11
 * Time: 11:29
 * To change this template use File | Settings | File Templates.
 */
public class GrabberRia extends ExactGrabber {
    private static final String searchURL = "http://ria.ru/search/?query=";
    private static final String sourceURL = "http://ria.ru";
    @Override
    public void setBrandNames(List<String> brandNames) {
        this.brandNames = brandNames;
    }

    @Override
    public void grab() {
        //List<String> result = new ArrayList<String>();
        try {
            for(String oneBrandName : brandNames){
                ScraperConfiguration config = new ScraperConfiguration(this.config);
                Scraper scraper = new Scraper(config, "/temporary");
                scraper.setDebug(true);
                scraper.addRuntimeListener(new RiaNewsScraperRuntimeListener(this.jdbcTemplate));
                scraper.addVariableToContext("riaQueryURL",searchURL+oneBrandName+"&p=");//"$p" - suffix for result page number
                scraper.addVariableToContext("riaAbsoluteURL",sourceURL);
                scraper.execute();
            }
            //result.add(scraper.getContext().getVar("temp").toString());
        } catch (Exception exception) {
            //exception.printStackTrace();
        }

        //for(String resultString : result){
            //К этому моменту все данные уже записаны в базу данных
        //    out.println(resultString);      //you must simply parse this result and add to db
        //}
    }

    @Override
    public void setConfig(String config) {
        this.config=config;
    }

    @Override
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public static void main(String[] args){
        try{
            GrabberRia grabberRia= new GrabberRia();
            List<String> testBrandNames = new ArrayList<String>();
            testBrandNames.add("apple");
            grabberRia.setBrandNames(testBrandNames);
            grabberRia.setConfig("miner/configs/riaNews1.xml");
            grabberRia.grab();
        }
        catch(Exception e){

        }
    }
}
