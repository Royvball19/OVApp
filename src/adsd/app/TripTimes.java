package adsd.app;

import org.json.JSONObject;

public class TripTimes
{
    // Attributes
    private double depTime;
    private double arivTime;
    private String vehicleType;
    private Integer travelTime;

    // Standard constructor
    public TripTimes(double depTime, double arivTime, String vehicleType, Integer travelTime)
    {
        this.depTime = depTime;
        this.arivTime = arivTime;
        this.vehicleType = vehicleType;
        this.travelTime = travelTime;
    }

    // JSON Constructor
    public TripTimes(JSONObject object)
    {
        depTime = object.getDouble("depTime");
        arivTime = object.getDouble("arivTime");
        vehicleType = object.getString("vehicleType");
        travelTime = object.getInt("travelTime");

    }

    // toJSON Method
    public JSONObject toJSON()
    {

        JSONObject jobj = new JSONObject();

        jobj.put("depTime", depTime);
        jobj.put("arivTime", arivTime);
        jobj.put("vehicleType", vehicleType);
        jobj.put("travelTime", travelTime);

        return jobj;
    }

    // Getters and Setters
    public double getDepTime()
    {
        return this.depTime;
    }

    public void setDepTime(double depTime)
    {
        this.depTime = depTime;
    }

    public double getArivTime()
    {
        return this.arivTime;
    }

    public void setArivTime(double arivTime)
    {
        this.arivTime = arivTime;
    }

    public String getVehicleType()
    {
        return this.vehicleType;
    }

    public void setVehicleType(String vehicleType)
    {
        this.vehicleType = vehicleType;
    }

    public Integer getTravelTime()
    {
        return this.travelTime;
    }

    public void setTravelTime(Integer travelTime)
    {
        this.travelTime = travelTime;
    }

}

