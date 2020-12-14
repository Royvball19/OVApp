package adsd.app;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class Profile
{
    // Attributes
    private int ID;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String residence;
    private int age;
    private ArrayList<Trip> myTripList;
    private ArrayList<FavoriteTrip> myFavoriteTrip;

    public Profile () {

    }

    // Standard Constructor
    public Profile(int ID, String userName, String password, String firstName, String lastName, String residence, int age)
    {
        this.ID         = ID;
        this.userName   = userName;
        this.password   = password;
        this.firstName  = firstName;
        this.lastName   = lastName;
        this.residence  = residence;
        this.age        = age;
        this.myFavoriteTrip = new ArrayList<FavoriteTrip>();
    }

    // JSON Constructor
    public Profile(JSONObject object)
    {
        ID          = object.getInt("ID");
        userName    = object.getString("userName");
        password    = object.getString("password");
        firstName   = object.getString("firstName");
        lastName    = object.getString("lastName");
        residence   = object.getString("residence");
        age         = object.getInt("age");

        myFavoriteTrip  = new ArrayList<FavoriteTrip>();
        JSONArray favTrips = object.getJSONArray("myFavoriteTrip");
        for (int i=0; i<favTrips.length(); i++)
        {
            FavoriteTrip p = new FavoriteTrip ((JSONObject) favTrips.get(i));
            myFavoriteTrip.add(p);
        }
    }

    // toJSON Method
    public JSONObject toJSON()
    {
        JSONObject jobj = new JSONObject();

        jobj.put("ID", ID);
        jobj.put("userName", userName);
        jobj.put("password", password);
        jobj.put("firstName", firstName);
        jobj.put("lastName", lastName);
        jobj.put("residence", residence);
        jobj.put("age", age);

        JSONArray ja = new JSONArray();
        for (FavoriteTrip t : myFavoriteTrip)
        {
            ja.put(t.toJSON());

        }
        jobj.put("myFavoriteTrip", ja);

        return jobj;
    }

    // addTrip Method
/*    public void addTrip(String locationFrom, String locationTo, String price, String distance, String travelTime, double locationFromLat, double locationFromLon, double locationToLat, double locationToLon)
    {
        myTripList.add(new Trip(locationFrom,locationTo, price, distance, travelTime, locationFromLat, locationFromLon, locationToLat, locationToLon));
    }*/

    public void addFavoriteTrip(int ID, String locationFrom, String locationTo)
    {
        myFavoriteTrip.add(new FavoriteTrip(ID, locationFrom, locationTo));
    }

    // Getters and Setters
    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getResidence() {
        return this.residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<FavoriteTrip> getMyFavTrip(){
        return myFavoriteTrip;
    }
}
