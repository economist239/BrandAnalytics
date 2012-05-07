package ru.brandanalyst.core.util;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: daddy-bear
 * Date: 2/25/12
 * Time: 12:26 PM
 */
@Deprecated
public class Time {
    private Time() {
    }

    public static Date getSimpleDate(final Date date) {
        return new Date(date.getYear(), date.getMonth(), date.getDay());
    }

}
