package ru.brandanalyst.core.model;

/**
 * Created by IntelliJ IDEA.
 * User: 1
 * Date: 09.10.11
 * Time: 17:15
 * To change this template use File | Settings | File Templates.
 */
public class Brand {
    private int id;
    private String name;
    private String description;

    public Brand(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setAbout(String description) {
        this.description = description;
    }
}
