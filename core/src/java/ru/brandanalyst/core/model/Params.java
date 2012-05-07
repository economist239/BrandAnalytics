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
@Deprecated
public class Params {
    private final static String SPLITTER = ":";
    private final JSONObject json;
    private final String rawParams;

    private Params(final String rawParams) {
        json = new JSONObject();
        this.rawParams = rawParams;
    }

    private Params(final JSONObject json, final String rawParams) {
        this.json = json;
        this.rawParams = rawParams;
    }

    public static Params empty(final String rawParams) {
        return new Params(rawParams);
    }

    public static Params parseFromString(@NotNull String input) {
        if (input.isEmpty()) {
            return empty(input);
        }
        try {
            return new Params(new JSONObject(input), input);
        } catch (JSONException e) {
            // временное
            return empty(input);
            //    throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return json.toString();
    }

    public String getRawParams() {
        return rawParams;
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
