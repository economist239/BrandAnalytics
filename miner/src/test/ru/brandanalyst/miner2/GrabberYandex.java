/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.brandanalyst.miner2;

import org.webharvest.runtime.*;
import org.webharvest.definition.ScraperConfiguration;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Александр
 */
public class GrabberYandex extends GrabberScriptUser implements Grabber {
    GrabberYandex() {
    }

    public List<String> grab() {
        List<String> result = new ArrayList<String>();
        try {
            ScraperConfiguration config = new ScraperConfiguration("config/config1.xml");
            Scraper scraper = new Scraper(config, ".");
            for(int i=0;i<targetUrls.length;++i){
                scraper.addVariableToContext("targetUrl",targetUrls[i].toString());
                scraper.setDebug(true);
                scraper.execute();
                result.add(scraper.getContext().getVar("temp").toString());
            }

        } catch (Exception exception) {
            result.add(exception.toString());
            return result;
        }
        return result;
    }

}
