package ru.brandanalyst.miner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/14/11
 * Time: 11:47 AM
 * container of grabbes that process all grabbers
 */
public class GrabberHandler implements InitializingBean {
    private static final Logger log = Logger.getLogger(GrabberHandler.class);
    private static final int DATE_STRING_LENGTH = 19;

    private List<Grabber> grabberList;


    public void setGrabberList(List<Grabber> grabberList) {
        this.grabberList = grabberList;
    }

    @Override
    public void afterPropertiesSet() {
        Date timeLimit;
        Date now = new Date();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader("miner/config/miner.cfg"));
            String date = bufferedReader.readLine().substring(0, DATE_STRING_LENGTH);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
            timeLimit = dateFormat.parse(date);
            bufferedReader.close();
        } catch (Exception e) {
            throw new RuntimeException("invalid file with date", e);
        }

        log.info("miner started...");
        if (grabberList != null) {
            for (Grabber g : grabberList) {
                g.grab(timeLimit);
            }
        }

        PrintWriter pw;
        try {
            pw = new PrintWriter(new File("miner/config/miner.cfg"));
            pw.write(now.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            log.error("error in date writtnig");
        }
        log.info("miner finished.");
    }
}
