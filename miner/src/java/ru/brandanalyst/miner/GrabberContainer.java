package ru.brandanalyst.miner;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/14/11
 * Time: 11:47 AM
 */
public class GrabberContainer {

    private static final Logger log = Logger.getLogger(GrabberContainer.class);

    private List<Grabber> grabberList;

    public void setGrabberList(List<Grabber> grabberList) {
        this.grabberList = grabberList;
    }

    public void afterPropertiesSet() {

        log.info("miner started...");
        if (grabberList != null) {
            for (Grabber g : grabberList) {
                g.grab();
            }
        }
        log.info("miner finished.");
    }
}
