package ru.brandanalyst.core.model;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 09.10.11
 * Time: 21:15
 * general model of infosource
 */
public class InfoSource {
    private final long id;
    private final long sphereId;
    private final String title;
    private final String description;
    private final String website;
    private final String rssSource;

    public InfoSource(long id, long sphereId, String title, String description, String website, String rssSource) {
        this.id = id;
        this.sphereId = sphereId;
        this.title = title;
        this.description = description;
        this.website = website;
        this.rssSource = rssSource;
    }

    public long getId() {
        return id;
    }

    public long getSphereId() {
        return sphereId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsite() {
        return website;
    }

    public String getRssSource() {
        return rssSource;
    }
}
