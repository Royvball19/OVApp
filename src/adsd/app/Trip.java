package adsd.app;

import org.json.JSONObject;

import java.util.Locale;
import java.util.ResourceBundle;

public class Trip {
    // Attributes
    private String locationFrom;
    private String locationTo;
    private String price;
    private String distance;
    private String travelTime;
    private String arrivalTime;
    private String departureTime;

    DataHandler dataHandler = new DataHandler();


    // Standard Constructor
    public Trip(String locationFrom, String locationTo, String price, String distance, String travelTime, String departureTime, String arrivalTime) {
        this.locationFrom = locationFrom;
        this.locationTo = locationTo;
        this.price = price;
        this.distance = distance;
        this.travelTime = travelTime;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    // JSON Constructor
    public Trip(JSONObject object) {
        locationFrom    = object.getString("locationFrom");
        locationTo      = object.getString("locationTo");
        price           = object.getString("price");
        distance        = object.getString("distance");
        travelTime      = object.getString("travelTime");
        departureTime   = object.getString("departureTime");
        arrivalTime     = object.getString("arrivalTime");
    }

    // toJSON Method
    public JSONObject toJSON() {
        JSONObject jobj = new JSONObject();

        jobj.put("locationFrom", locationFrom);
        jobj.put("locationTo", locationTo);
        jobj.put("price", price);
        jobj.put("distance", distance);
        jobj.put("travelTime", travelTime);
        jobj.put("departureTime", departureTime);
        jobj.put("arrivalTime", arrivalTime);
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

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrivalTime(){
        return this.arrivalTime;
    }

    public void setDepartureTime(){
        this.departureTime = departureTime;
    }

    public String getDepartureTime(){
        return  this.departureTime;
    }


    public void printTripDetails() {
        Locale.setDefault(new Locale("nl", "NL"));
        ResourceBundle rb = ResourceBundle.getBundle("lang");

        System.out.println(rb.getString("detailsPrint"));
        System.out.println(rb.getString("fromPrint")+ locationFrom );
        System.out.println(rb.getString("toPrint")+ locationTo);
        System.out.println(rb.getString("pricePrint")+ price);
        System.out.println(rb.getString("distancePrint")+ distance+ " " + rb.getString("kmPrint"));
        System.out.println(rb.getString("traveltimePrint")+ travelTime+ " " + rb.getString("minPrint"));

    }
}
