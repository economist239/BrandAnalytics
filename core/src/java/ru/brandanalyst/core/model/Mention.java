package ru.brandanalyst.core.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.brandanalyst.core.util.Jsonable;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: alexsen
 * Date: 23.02.12
 * Time: 20:36
 */
public class Mention implements Jsonable {
    private SingleDot dot;
    private String ticker;
    private String brand;
    private int tickerId;
    private int brandId;

    public Mention(SingleDot dot, String ticker, String brand, int tickerId, int brandId) {
        this.dot = dot;
        this.ticker = ticker;
        this.brand = brand;
        this.tickerId = tickerId;
        this.brandId = brandId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public SingleDot getDot() {
        return dot;
    }

    public void setDot(SingleDot dot) {
        this.dot = dot;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getTickerId() {
        return tickerId;
    }

    public void setTickerId(int tickerId) {
        this.tickerId = tickerId;
    }

    @Override
    public JSONObject asJson() {
        //count millisecs in sec
        final long COUNT = 1000;
        try {
            return new JSONObject()
                    .put("ticker-id",tickerId)
                    .put("ticker", ticker)
                    .put("num", (int)dot.getValue())
                    .put("brand-id",brandId)
                    .put("brand", brand);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
