/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.brandanalyst.miner;

import org.webharvest.runtime.*;
import org.webharvest.definition.ScraperConfiguration;

/**
 * @author Александр
 */
public class GrabberYandex implements Grabber {
    String cfg;

    GrabberYandex(String cfg) {
        this.cfg = cfg;
    }

    public String[] grab() {
        String[] sp = new String[1];
        try {
            ScraperConfiguration config = new ScraperConfiguration("miner/configs/config1.xml");
         //   ScraperConfiguration config = new ScraperConfiguration(cfg);
            Scraper scraper = new Scraper(config, ".");
            scraper.setDebug(true);
            scraper.execute();
            sp[0] = scraper.getContext().getVar("temp").toString();
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }

        return sp;

    }

}
