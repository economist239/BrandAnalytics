package ru.brandanalyst.frontend.model;

/**
 * Created by IntelliJ IDEA.
 * User: 1
 * Date: 05.10.11
 * Time: 1:54
 * To change this template use File | Settings | File Templates.
 */
public class Graph {
    private String dots;
    private String description;

    public Graph(String dots, String description) {
        this.dots = dots;
        this.description = description;
    }

    public void setDots(String dots) {
        this.dots = dots;
    }

    public String getDots() {
         return dots;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
         return description;
    }
}
