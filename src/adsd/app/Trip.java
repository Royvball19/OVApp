package adsd.app;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class Trip
{
    // Attributes
    private Integer ID;
    private String locationFrom;
    private String locationTo;
    private String distance;
    private double locationFromLat;
    private double locationFromLng;
    private double locationToLat;
    private double locationToLng;
    private ArrayList<TripTimes> times;
    private ArrayList<TripExtra> extras;


    // Standard Constructor
    public Trip(Integer ID, String locationFrom, String locationTo, String distance, double locationFromLat, double locationFromLng, double locationToLat, double locationToLng)
    {
        this.ID              = ID;
        this.locationFrom    = locationFrom;
        this.locationTo      = locationTo;
        this.distance        = distance;
        this.locationFromLat = locationFromLat;
        this.locationFromLng = locationFromLng;
        this.locationToLat   = locationToLat;
        this.locationToLng   = locationToLng;
        this.times  = new ArrayList<TripTimes>();
        this.extras = new ArrayList<TripExtra>();

    }

    public Trip(String locationFrom, String locationTo)
    {
        this.locationFrom = locationFrom;
        this.locationTo   = locationTo;
        this.times        = new ArrayList<TripTimes>();
        this.extras       = new ArrayList<TripExtra>();
    }

    // JSON Constructor
    public Trip(JSONObject object)
    {
        ID = object.getInt("ID");
        locationFrom = object.getString("locationFrom");
        locationTo = object.getString("locationTo");
        distance = object.getString("distance");
        locationFromLat = object.getDouble("locationFromLat");
        locationFromLng = object.getDouble("locationFromLng");
        locationToLat = object.getDouble("locationToLat");
        locationToLng = object.getDouble("locationToLng");

        times = new ArrayList<TripTimes>();
        JSONArray time = object.getJSONArray("tripTimes");
        for (int i=0; i<time.length(); i++)
        {
            TripTimes p = new TripTimes ((JSONObject) time.get(i));

            times.add(p);
        }

        extras = new ArrayList<TripExtra>();
        JSONArray extra = object.getJSONArray("tripExtras");
        for (int i=0; i<extra.length(); i++)
        {
            TripExtra e = new TripExtra ((JSONObject) extra.get(i));

            extras.add(e);
        }

    }

    // toJSON Method
    public JSONObject toJSON()
    {

        JSONObject jobj = new JSONObject();

        jobj.put("ID", ID);
        jobj.put("locationFrom", locationFrom);
        jobj.put("locationTo", locationTo);
        jobj.put("distance", distance);
        jobj.put("locationFromLat", locationFromLat);
        jobj.put("locationFromLng", locationFromLng);
        jobj.put("locationToLat", locationToLat);
        jobj.put("locationToLng", locationToLng);

        JSONArray ja = new JSONArray();
        for (TripTimes t : times)
        {
            ja.put(t.toJSON());

        }
        jobj.put("tripTimes", ja);

        JSONArray nee = new JSONArray();
        for (TripExtra e : extras)
        {
            nee.put(e.toJSON());

        }
        jobj.put("tripExtras", nee);

        return jobj;
    }

    public void addTime(double depTime, double arivTime, String vehicleType, Integer travelTime)
    {
        times.add(new TripTimes(depTime, arivTime, vehicleType, travelTime));
    }

    // Getters and Setters
    public int getID()
    {
        return this.ID;
    }

    public void setID(Integer ID)
    {
        this.ID = ID;
    }


    public String getLocationFrom()
    {
        return this.locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom;
    }

    public String getLocationTo()
    {
        return this.locationTo;
    }

    public void setLocationTo(String locationTo)
    {
        this.locationTo = locationTo;
    }

    public String getDistance()
    {
        return distance;
    }

    public void setDistance(String distance)
    {
        this.distance = distance;
    }

    public double getLocationFromLat()
    {
        return locationFromLat;
    }

    public void setLocationFromLat(double locationFromLat)
    {
        this.locationFromLat = locationFromLat;
    }

    public double getLocationFromLng()
    {
        return locationFromLng;
    }

    public void setLocationFromLng(double locationFromLng)
    {
        this.locationFromLng = locationFromLng;
    }

    public double getLocationToLat()
    {
        return locationToLat;
    }

    public void setLocationToLat(double locationToLat)
    {
        this.locationToLat = locationToLat;
    }

    public double getLocationToLng()
    {
        return locationToLng;
    }

    public void setLocationToLng(double locationToLng)
    {
        this.locationToLng = locationToLng;
    }

    public ArrayList<TripTimes> getTripTimesList()
    {
        return times;
    }

    public ArrayList<TripExtra> getTripExtrasList()
    {
        return extras;
    }

    public TripTimes getTripTimeObj(int i) {
        return times.get(i);
    }
}
