package ru.brandanalyst.core.model;

import org.json.JSONException;
import org.json.JSONObject;
import ru.brandanalyst.core.util.Jsonable;

/**
 * Модель бренда
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 09.10.11
 * Time: 17:15
 */
public class Brand implements Jsonable{
    private long id;
    private long branchId;
    private String name;
    private String description;
    private String website;
    private String finamName;

    public Brand(long id, String name, String description, String website, long branchId, String finamName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.branchId = branchId;
        this.finamName = finamName;
    }

    public Brand(long id, String name, String description, String website) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.branchId = -1;
        this.finamName = "";
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

    public String getFinamName() {
        return finamName;
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

    @Override
    public JSONObject asJson() {
        try {
            return new JSONObject().put("brand", name);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
