package ru.brandanalyst.core.model;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/16/11
 * Time: 7:22 PM
 * general model of one dot
 */
public class SingleDot implements Comparable<SingleDot>{
    private final Timestamp date;
    private final double value;

    public SingleDot(Timestamp date, double value) {
        this.date = date;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public Timestamp getDate() {
        return date;
    }

    @Override
    public int compareTo(SingleDot o) {
        return this.getDate().compareTo(o.getDate());
    }
}
