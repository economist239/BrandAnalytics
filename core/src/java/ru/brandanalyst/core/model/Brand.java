package ru.brandanalyst.core.model;

/**
 * Created by IntelliJ IDEA.
 * User: 1
 * Date: 09.10.11
 * Time: 17:15
 * To change this template use File | Settings | File Templates.
 */
public class Brand {
    private long id;
    private String name;
    private String description;
    private String website;
    private String branch;

    public Brand(long id, String name, String description, String website, String branch) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.branch = branch;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String description) {
        this.description = description;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
    
}
