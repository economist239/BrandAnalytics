package ru.brandanalyst.miner;

import java.io.BufferedReader;
import java.io.FileReader;
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
    private static final int DATE_STRING_LENGTH = 19;

    private List<Grabber> grabberList;


    public void setGrabberList(List<Grabber> grabberList) {
        this.grabberList = grabberList;
    }

    public void afterPropertiesSet() {

        Date timeLimit;
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader("miner/config/miner.cfg"));
            String date = bufferedReader.readLine().substring(0, DATE_STRING_LENGTH);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
            timeLimit = dateFormat.parse(date);
            bufferedReader.close();
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
