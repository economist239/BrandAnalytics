package ru.brandanalyst.core.model;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 09.10.11
 * Time: 17:15
 */
public class Brand {
    private long id;
    private long branchId;
    private String name;
    private String description;
    private String website;

    public Brand(long id, String name, String description, String website, long branchId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.branchId = branchId;
    }

    public Brand(long id, String name, String description, String website) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.branchId = -1;
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

    public long getBranchId() {
        return branchId;
    }

    public void setBranch(long branchId) {
        this.branchId = branchId;
    }
    
}
