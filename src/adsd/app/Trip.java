package adsd.app;

import org.json.JSONObject;

public class Trip
{
    // Attributes
    private String locationFrom;
    private String locationTo;

    // Standard Constructor
    public Trip(String locationFrom, String locationTo)
    {
        this.locationFrom = locationFrom;
        this.locationTo   = locationTo;
    }

    // JSON Constructor
    public Trip(JSONObject object)
    {
        locationFrom  = object.getString("locationFrom");
        locationTo    = object.getString("locationTo");
    }

    // toJSON Method
    public JSONObject toJSON()
    {
        JSONObject jobj = new JSONObject();

        jobj.put("locationFrom", locationFrom);
        jobj.put("locationTo", locationTo);
        return jobj;
    }

    // Getters and Setters
    public String getLocationFrom() {
        return this.locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom;
    }

    public String getLocationTo() {
        return this.locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
    }
}
