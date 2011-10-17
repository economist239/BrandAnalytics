package ru.brandanalyst.core.model;

import java.sql.Timestamp;
/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 10/16/11
 * Time: 7:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class SingleDot {
    Timestamp date;
    double value;
    void SingleDot(Timestamp date, double value) {
        this.date = date;
        this.value = value;
    }
}
