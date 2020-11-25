package adsd.app;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;

public class DataHandler {
    final String filename = "data.json";


    private ArrayList<Profile> profiles = new ArrayList<>();
    private ArrayList<Trip> trips = new ArrayList<>();

    // writeToJSON Method to write data to JSON
    public void writeToJSON() throws FileNotFoundException, UnsupportedEncodingException {

        PrintWriter writer = new PrintWriter(filename, "UTF-8");

        String p;
        for (Profile q : profiles) {
            p = q.toJSON().toString();
            writer.println(p);
        }

        writer.println(",");

        String t;
        for (Trip q : trips) {
            t = q.toJSON().toString();
            writer.println(t);
        }

        writer.flush();
        writer.close();
    }

    // readFromJSON Method to read data from JSON
    public void readFromJSON() throws FileNotFoundException {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.format("ERROR: file %s does not exist.\n", filename);
            System.exit(0);
        }
        InputStream is = new FileInputStream(file);
        JSONTokener tokener = new JSONTokener(is);

        while (true) {
            try {
                JSONObject object = new JSONObject(tokener);
                Profile newProfile = new Profile(object);
                profiles.add(newProfile);
            } catch (org.json.JSONException e) {
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        while (true) {
            try {
                JSONObject tripObject = new JSONObject(tokener);
                Trip newTrip = new Trip(tripObject);
                trips.add(newTrip);
            } catch (org.json.JSONException e) {
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // getProfile Method to get index from ArrayList
    public Profile getProfile(int index) {
        return profiles.get(index);
    }

    // getTrip Method to get index from ArrayList
    public Trip getTrip(int index) {
        return trips.get(index);
    }

    public ArrayList<Trip> getTripList(){
        return trips;
    }

}
