/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabber;

import org.webharvest.runtime.*;
import org.webharvest.definition.ScraperConfiguration;




/**
 * @author Александр
 */
public class GrabberYandex implements Grabber {
    GrabberYandex() {
    }

    public String[] grab() {
        String[] sp = new String[1];
        try {
            ScraperConfiguration config = new ScraperConfiguration("config/config1.xml");
            Scraper scraper = new Scraper(config, ".");
            scraper.setDebug(true);
            scraper.execute();
            sp[0] = scraper.getContext().getVar("temp").toString();
        } catch (Exception exception) {
            sp[0] = exception.toString();
        }

        return sp;

    }

}
