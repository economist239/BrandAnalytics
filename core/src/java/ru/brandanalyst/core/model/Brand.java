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
public class Brand implements Jsonable {
    private long id;
    private long branchId;
    private String name;
    private String description;
    private String website;
    private Params params;

    public Brand(long id, String name, String description, String website, long branchId, Params params) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.branchId = branchId;
        this.params = params;
    }

    public Brand(long id, String name, String description, String website) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.branchId = -1;
        this.params = Params.empty();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsite() {
        return website;
    }

    public long getBranchId() {
        return branchId;
    }

    public Params getParams() {
        return params;
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
