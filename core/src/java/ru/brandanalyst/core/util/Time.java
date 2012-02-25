package ru.brandanalyst.core.util;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: daddy-bear
 * Date: 2/25/12
 * Time: 12:26 PM
 */
public class Time {
    private Time() {
    }

    public static Timestamp getSimpleDate(final Timestamp date) {
        return new Timestamp(new Date(date.getYear(), date.getMonth(), date.getDay()).getTime());
    }
}
