package adsd.app;

import org.json.JSONObject;

public class TripExtra
{
    // Attributes
    private boolean bicycleSpots;
    private boolean toilet;
    private String trainClass;

    // Constructor
    public TripExtra(boolean bicycleSpots, boolean toilet, String trainClass)
    {
        this.bicycleSpots = bicycleSpots;
        this.toilet       = toilet;
        this.trainClass   = trainClass;
    }

    public TripExtra(JSONObject object)
    {
        bicycleSpots = object.getBoolean("bicycleSpots");
        toilet = object.getBoolean("toilet");
        trainClass = object.getString("trainClass");
    }

    // toJSON Method
    public JSONObject toJSON()
    {
        JSONObject jobj = new JSONObject();

        jobj.put("bicycleSpots", bicycleSpots);
        jobj.put("toilet", toilet);
        jobj.put("trainClass", trainClass);

        return jobj;
    }

    //Getters
    public boolean getBicycleSpots()
    {
        return this.bicycleSpots;
    }

    public boolean getToilet()
    {
        return this.toilet;
    }

    public String getTrainClass()
    {
        return this.trainClass;
    }



    //Setters
    public void setBicycleSpots(boolean bicycleSpots)
    {
        this.bicycleSpots = bicycleSpots;
    }

    public void setToilet(boolean toilet)
    {
        this.toilet = toilet;
    }

    public void setTrainClass(String trainClass)
    {
        this.trainClass = trainClass;
    }
}
