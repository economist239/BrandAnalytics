package ru.brandanalyst.frontend.models;

import ru.brandanalyst.core.model.SingleDot;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/16/11
 * Time: 4:14 PM
 * model of graph for web
 */
public class GraphForWeb {
    private String name;
    private List<Integer> date;
    private List<Double> value;

    public GraphForWeb(String name, List<Integer> date, List<Double> value) {
        this.date = date;
        this.name = name;
        this.value = value;
    }

    public GraphForWeb(String name) {
        this.name = name;
        date = new ArrayList<Integer>();
        value = new ArrayList<Double>();
    }

    public void addDot(SingleDot dot) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(dot.getDate().getTime());

        date.add(calendar.get(GregorianCalendar.DAY_OF_MONTH));
        value.add(dot.getValue());
    }

    public String getName() {
        return name;
    }

    public List<Integer> getDate() {
        return date;
    }

    public List<Double> getValue() {
        return value;
    }
}
