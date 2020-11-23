package adsd.app;

import org.json.JSONObject;

public class Trip
{
    private String locationFrom;
    private String locationTo;

    public Trip(String locationFrom, String locationTo)
    {
        this.locationFrom = locationFrom;
        this.locationTo   = locationTo;
    }

    public Trip(JSONObject object)
    {
        locationFrom  = object.getString("locationFrom");
        locationTo    = object.getString("locationTo");
    }

    public JSONObject toJSON()
    {
        JSONObject jobj = new JSONObject();

        jobj.put("locationFrom", locationFrom);
        jobj.put("locationTo", locationTo);
        return jobj;
    }

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
