package adsd.app;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Profile
{
    // Attributes
    private String firstName;
    private String lastName;
    private ArrayList<Trip> myTripList;


    // Standard Constructor
    public Profile(String firstName, String lastName)
    {
        this.firstName  = firstName;
        this.lastName   = lastName;
        this.myTripList = new ArrayList<Trip>();
    }

    // JSON Constructor
    public Profile(JSONObject object)
    {
        firstName   = object.getString("firstName");
        lastName    = object.getString("lastName");

        myTripList  = new ArrayList<Trip>();
        JSONArray trips = object.getJSONArray("myTripList");
        for (int i=0; i<trips.length(); i++)
        {
            Trip p = new Trip ((JSONObject) trips.get(i));
            myTripList.add(p);
        }
    }

    // toJSON Method
    public JSONObject toJSON()
    {
        JSONObject jobj = new JSONObject();

        jobj.put("firstName", firstName);
        jobj.put("lastName", lastName);

        JSONArray ja = new JSONArray();
        for (Trip t : myTripList)
        {
            ja.put(t.toJSON());

        }
        jobj.put("myTripList", ja);

        return jobj;
    }

    // addTrip Method
    public void addTrip(String locationFrom, String locationTo)
    {
        myTripList.add(new Trip(locationFrom,locationTo));
    }

    // Testprint Method
    public void print()
    {
        System.out.println("Naam: " + firstName + " " + lastName);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
