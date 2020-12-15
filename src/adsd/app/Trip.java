package adsd.app;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class Trip {
    // Attributes
    private Integer ID;
    private String locationFrom;
    private String locationTo;
    private String vehicle;
    private String distance;
    private String travelTime;
    private double locationFromLat;
    private double locationFromLng;
    private double locationToLat;
    private double locationToLng;
    private ArrayList times = new ArrayList();


    // Standard Constructor
    public Trip(Integer ID, String locationFrom, String locationTo, String vehicle, String distance, String travelTime, double locationFromLat, double locationFromLng, double locationToLat, double locationToLng) {
        this.ID              = ID;
        this.locationFrom    = locationFrom;
        this.locationTo      = locationTo;
        this.vehicle         = vehicle;
        this.distance        = distance;
        this.travelTime      = travelTime;
        this.locationFromLat = locationFromLat;
        this.locationFromLng = locationFromLng;
        this.locationToLat   = locationToLat;
        this.locationToLng   = locationToLng;
    }

    // JSON Constructor
    public Trip(JSONObject object) {
        ID = object.getInt("ID");
        locationFrom = object.getString("locationFrom");
        locationTo = object.getString("locationTo");
        vehicle = object.getString("vehicle");
        distance = object.getString("distance");
        travelTime = object.getString("travelTime");
        locationFromLat = object.getDouble("locationFromLat");
        locationFromLng = object.getDouble("locationFromLng");
        locationToLat = object.getDouble("locationToLat");
        locationToLng = object.getDouble("locationToLng");

    }

    // toJSON Method
    public JSONObject toJSON() {
        JSONObject jobj = new JSONObject();

        jobj.put("ID", ID);
        jobj.put("locationFrom", locationFrom);
        jobj.put("locationTo", locationTo);
        jobj.put("vehicle", vehicle);
        jobj.put("distance", distance);
        jobj.put("travelTime", travelTime);
        jobj.put("locationFromLat", locationFromLat);
        jobj.put("locationFromLng", locationFromLng);
        jobj.put("locationToLat", locationToLat);
        jobj.put("locationToLng", locationToLng);
        return jobj;
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

    public String getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTravelTime() {
        return this.travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public double getLocationFromLat() {
        return locationFromLat;
    }

    public void setLocationFromLat(double locationFromLat) {
        this.locationFromLat = locationFromLat;
    }

    public double getLocationFromLng() {
        return locationFromLng;
    }

    public void setLocationFromLng(double locationFromLng) {
        this.locationFromLng = locationFromLng;
    }

    public double getLocationToLat() {
        return locationToLat;
    }

    public void setLocationToLat(double locationToLat) {
        this.locationToLat = locationToLat;
    }

    public double getLocationToLng() {
        return locationToLng;
    }

    public void setLocationToLng(double locationToLng) {
        this.locationToLng = locationToLng;
    }

    public void addTripTime(int index, String depTime, String arrTime){

        times.add(1,"10.00");

    }
}
