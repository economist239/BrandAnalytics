package ru.brandanalyst.core.model.simple;

import ru.brandanalyst.core.model.SingleDot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    public List<String> getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> textDateList = new LinkedList();
        for(long d: date) {
            textDateList.add(dateFormat.format(new Date(d)));
        }
        return textDateList;
    }

    public List<Double> getValue() {
        return value;
    }
}
