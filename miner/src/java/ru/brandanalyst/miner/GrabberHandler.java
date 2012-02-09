package ru.brandanalyst.miner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import ru.brandanalyst.miner.grabbers.Grabber;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/14/11
 * Time: 11:47 AM
 * container of grabbes that process all grabbers
 */
public class GrabberHandler extends TimerTask {


    private static final Logger log = Logger.getLogger(GrabberHandler.class);

    private long period;
    private List<Grabber> grabberList;

    public void setPeriod(long period) {
        this.period = period;
    }

    public void setGrabberList(List<Grabber> grabberList) {
        this.grabberList = grabberList;
    }

    @Override
    public void run() {
        log.info("miner started...");
        if (grabberList != null) {
            for (Grabber g : grabberList) {
                g.grab(new Date());
            }
        }
    }
}
