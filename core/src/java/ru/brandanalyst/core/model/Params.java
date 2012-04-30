package ru.brandanalyst.core.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/9/12
 * Time: 8:27 PM
 */
public class Params {
    private final static String SPLITTER = ":";
    private JSONObject json;

    private Params() {
        json = new JSONObject();
    }

    private Params(JSONObject json) {
        this.json = json;
    }

    public static Params empty() {
        return new Params();
    }

    public static Params parseFromString(@NotNull String input) {
        if (input.isEmpty()) {
            return empty();
        }
        try {
            return new Params(new JSONObject(input));
        } catch (JSONException e) {
            // временное
            return empty();
            //    throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return json.toString();
    }

    /*
   *
   * path in format like "path1:path2", where ":" - separator
    */
    @Nullable
    public String getValue(String path) {
        String[] pathCrumbs = path.split(SPLITTER);
        JSONObject next = new JSONObject(json);
        try {
            for (String crumb : pathCrumbs) {
                Object nextVal = next.get(crumb);
                if (nextVal == null) {
                    return null;
                }
                next = new JSONObject(next.get(crumb));
            }
            return next.toString();
        } catch (JSONException e) {
            return null;
        }
    }
}
