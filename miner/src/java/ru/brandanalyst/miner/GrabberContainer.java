package ru.brandanalyst.miner;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 10/14/11
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class GrabberContainer {

    private List<Grabber> grabberList;
    private List<ExactGrabber> exactGrabberList;

    public void setGrabberList (List<Grabber> grabberList) {
        this.grabberList = grabberList;
    }

    public void setExactGrabberList (List<ExactGrabber> exactGrabberList) {
        this.exactGrabberList = exactGrabberList;
    }

    public void afterPropertiesSet () {
        System.out.println("miner started");


        for(Grabber g: grabberList) {
            g.grab();
        }

        for(ExactGrabber g: exactGrabberList) {
            g.grab();
        }
    }
}
