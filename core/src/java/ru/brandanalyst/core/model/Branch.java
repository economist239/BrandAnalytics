package ru.brandanalyst.core.model;

/**
 * Created by IntelliJ IDEA.
 * User: Obus
 * Date: 18.02.12
 * Time: 9:56
 */
public class Branch {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Branch(int id, String name) {

        this.id = id;
        this.name = name;
    }
}
