package ru.brandanalyst.miner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/14/11
 * Time: 11:47 AM
 * container of grabbes that process all grabbers
 */
public class GrabberContainer {
    private static final Logger log = Logger.getLogger(GrabberContainer.class);

    private List<Grabber> grabberList;

    public void setGrabberList(List<Grabber> grabberList) {
        this.grabberList = grabberList;
    }

    public void afterPropertiesSet() {

        Date timeLimit;
        try {
            String date = new BufferedReader(new FileReader("miner/config/miner.cfg")).readLine().substring(0,19);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
            timeLimit = dateFormat.parse(date);
        } catch (Exception e) {
            timeLimit = new Date();
        }

        log.info("miner started...");
        if (grabberList != null) {
            for (Grabber g : grabberList) {
                g.grab(timeLimit);
            }
        }
        log.info("miner finished.");
    }
}
