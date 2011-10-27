package ru.brandanalyst.frontend.models;

import ru.brandanalyst.core.model.SingleDot;

import java.util.List;
import java.util.ArrayList;
/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/16/11
 * Time: 4:14 PM
 * model of graph for web
 */
public class GraphForWeb {
    private String name;
    private List<Long> date;
    private List<Double> value;

    public GraphForWeb(String name, List<Long> date, List<Double> value) {
        this.date = date;
        this.name = name;
        this.value = value;
    }

    public GraphForWeb(String name) {
        this.name = name;
        date = new ArrayList<Long>();
        value = new ArrayList<Double>();
    }

    public void addDot(SingleDot dot) {
        date.add(dot.getDate().getTime());
        value.add(dot.getValue());
    }

    public String getName() {
        return name;
    }

    public List<Long> getDate() {
        return date;
    }

    public List<Double> getValue() {
        return value;
    }
}
