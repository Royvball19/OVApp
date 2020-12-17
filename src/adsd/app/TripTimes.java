package adsd.app;

import org.json.JSONArray;
import org.json.JSONObject;

public class TripTimes {
    private double depTime;
    private double arivTime;

    //Standard constructor
    public TripTimes(double depTime, double arivTime) {
        this.depTime = depTime;
        this.arivTime = arivTime;
    }
    //JSON Constructor

    public TripTimes(JSONObject object) {
        depTime = object.getDouble("depTime");
        arivTime = object.getDouble("arivTime");

    }

    // toJSON Method
    public JSONObject toJSON() {

        JSONObject jobj = new JSONObject();

        jobj.put("depTime", depTime);
        jobj.put("arivTime", arivTime);

        return jobj;
    }

    public double getDepTime() {
        return this.depTime;
    }

    public void setTravelTime(double depTime) {
        this.depTime = depTime;
    }

    public double getArivTime() {
        return this.arivTime;
    }

    public void setArivTime(double arivTime) {
        this.arivTime = arivTime;
    }

}

