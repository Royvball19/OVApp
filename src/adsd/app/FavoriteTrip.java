package adsd.app;

public class FavoriteTrip {

    // Attributes
    private int ID;
    private String locationFrom;
    private String locationTo;
    private String locationFromLat;
    private String locationFromLng;
    private String locationToLat;
    private String locationToLng;

    // Standard Constructor
    public FavoriteTrip(int ID, String locationFrom, String locationTo, String locationFromLat, String locationFromLng, String locationToLat, String locationToLng)
    {
        this.ID = ID;
        this.locationFrom = locationFrom;
        this.locationTo = locationTo;
        this.locationFromLat = locationFromLat;
        this.locationFromLng = locationFromLng;
        this.locationToLat = locationToLat;
        this.locationToLng = locationToLng;
    }

    // Getters and Setters
    public int getID()
    {
        return this.ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public String getLocationFrom()
    {
        return this.locationFrom;
    }

    public void setLocationFrom(String locationFrom)
    {
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

    public String getLocationFromLat()
    {
        return this.locationFromLat;
    }

    public void setLocationFromLat(String locationFromLat)
    {
        this.locationFromLat = locationFromLat;
    }

    public String getLocationFromLng()
    {
        return this.locationFromLng;
    }

    public void setLocationFromLng(String locationFromLng)
    {
        this.locationFromLng = locationFromLng;
    }

    public String getLocationToLat()
    {
        return this.locationToLat;
    }

    public void setLocationToLat(String locationToLat)
    {
        this.locationToLat = locationToLat;
    }

    public String getLocationToLng()
    {
        return this.locationToLng;
    }

    public void setLocationToLng(String locationToLng)
    {
        this.locationFromLng = locationFromLng;
    }
}
