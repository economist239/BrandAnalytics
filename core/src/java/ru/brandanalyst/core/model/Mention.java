package ru.brandanalyst.core.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.brandanalyst.core.util.Jsonable;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Упоминание о бренде
 * Created by IntelliJ IDEA.
 * User: alexsen
 * Date: 23.02.12
 * Time: 20:36
 */
public class Mention implements Jsonable{
    private SingleDot dot;
    private long tickerId;
    private String brand;

    public Mention(SingleDot dot, long tickerId, String brandId){
        this.dot = dot;
        this.tickerId = tickerId;
        this.brand = brandId;
    }

    public SingleDot getDot(){
        return dot;
    }

    public void setDot(SingleDot dot){
        this.dot = dot;
    }

    public long getTickerId(){
        return tickerId;
    }

    public void setTickerId(long tickerId){
        this.tickerId = tickerId;
    }

    public String getBrandName(){
        return brand;
    }

    public void setBrandId(String brandId){
        this.brand = brandId;
    }

    @Override
    public JSONObject asJson(){
        //count millisecs in sec
        final long COUNT = 1000;
        try{
            return new JSONObject()
                    .put("ticker-id", tickerId)
                    .put("num", (int) dot.getValue())
                    .put("brand", brand);
        } catch(JSONException e){
            throw new RuntimeException(e);
        }
    }
}
