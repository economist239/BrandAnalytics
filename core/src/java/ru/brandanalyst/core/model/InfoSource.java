package ru.brandanalyst.core.model;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 09.10.11
 * Time: 21:15
 */
public class InfoSource {
    private int id;
    private int sphereId;
    private String title;
    private String description;
    private String website;

    public InfoSource(int id, int sphereId, String title, String description, String website) {
        this.id = id;
        this.sphereId = sphereId;
        this.title = title;
        this.description = description;
        this.website = website;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSphereId() {
        return sphereId;
    }

    public void setSphereId(int sphereId) {
        this.sphereId = sphereId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setWebsite(String website) {
        this.website = website;
    }
}
