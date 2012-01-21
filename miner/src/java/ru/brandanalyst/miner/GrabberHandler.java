package ru.brandanalyst.miner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import ru.brandanalyst.miner.grabbers.Grabber;

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
    private static final String PATH = "miner/config/miner.cfg";
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final int DATE_STRING_LENGTH = PATTERN.length();


    private List<Grabber> grabberList;


    public void setGrabberList(List<Grabber> grabberList) {
        this.grabberList = grabberList;
    }

    @Override
    public void afterPropertiesSet() {
        Date timeLimit;
        Date now = new Date();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(PATH));
            String date = bufferedReader.readLine().substring(0, DATE_STRING_LENGTH);
            SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN);
            timeLimit = dateFormat.parse(date);
            bufferedReader.close();
        } catch (Exception e) {
            throw new RuntimeException("invalid file with date", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }

        log.info("miner started...");
        if (grabberList != null) {
            for (Grabber g : grabberList) {
                g.grab(timeLimit);
            }
        }

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(PATH));
            SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN);
            pw.write(dateFormat.format(now));
            log.debug(PATH + " have changed");
        } catch (FileNotFoundException e) {
            log.error("error in date writtnig");
        } finally {
            if (pw != null)
                pw.close();
        }
        log.info("miner finished.");
    }
}
