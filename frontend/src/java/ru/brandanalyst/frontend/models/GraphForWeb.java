package ru.brandanalyst.frontend.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/16/11
 * Time: 4:14 PM
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
