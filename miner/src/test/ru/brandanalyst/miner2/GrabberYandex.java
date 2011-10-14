/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.brandanalyst.miner2;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.webharvest.runtime.*;
import org.webharvest.definition.ScraperConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;


/**
 * @author Александр
 */
//public class GrabberYandex extends GrabberScriptUser implements Grabber {
public class GrabberYandex extends Grabber {

    public void setConfig(String config) {
        this.config = config;  //not using    //config = urls + ";" + webharvestconfig // now its = "config/config1.xml"
    }

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void grab() {
        List<String> result = new ArrayList<String>();
        try {
            ScraperConfiguration config = new ScraperConfiguration(this.config);
            Scraper scraper = new Scraper(config, ".");
            scraper.setDebug(true);
            scraper.execute();
            result.add(scraper.getContext().getVar("temp").toString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        for(String resultString : result){
            out.println(resultString);      //you must simply parse this result and add to db
        }
    }

}
