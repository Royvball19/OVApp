package adsd.app;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class DataHandler {
    final String filename = "data.json";

    private String url = "jdbc:mysql://localhost:3306/database";
    private String username = "db";
    private String password = "wachtwoord";

    private ArrayList<Profile> profiles = new ArrayList<>();
    private ArrayList<Trip> trips = new ArrayList<>();
    private ArrayList<FavoriteTrip> favTrips = new ArrayList<>();


    // writeToJSON Method to write data to JSON
    public void writeToExternalData() throws FileNotFoundException, UnsupportedEncodingException {

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

        try
        {
            Connection conn = DriverManager.getConnection(url, username, password);

            // Create the java mysql update preparedstatement
            String updateUsersQuery = "update User set age = ?, residence = ? where id = ?;";
            PreparedStatement usersPreparedStmt = conn.prepareStatement(updateUsersQuery);

            for (int i = 0; i < profiles.size(); i++ )
            {
                usersPreparedStmt.setInt(1, profiles.get(i).getAge());
                usersPreparedStmt.setString(2, profiles.get(i).getResidence());
                usersPreparedStmt.setInt(3, profiles.get(i).getID());
                usersPreparedStmt.addBatch();
            }

            String updateTripsQuery = "update Trip set locationFrom = ?, locationTo = ? where id = ?;";
            PreparedStatement tripsPreparedStmt = conn.prepareStatement(updateTripsQuery);

            for (int i = 0; i < trips.size(); i++ )
            {
                tripsPreparedStmt.setString(1, trips.get(i).getLocationFrom());
                tripsPreparedStmt.setString(2, trips.get(i).getLocationTo());
                tripsPreparedStmt.setInt(3, trips.get(i).getID());
                tripsPreparedStmt.addBatch();
            }

            //todo FavoriteTrip

            //todo Favorites

            // Execute the java preparedstatement batches
            usersPreparedStmt.executeBatch();
            tripsPreparedStmt.executeBatch();

            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }



    // readFromJSON Method to read data from JSON
    public void readFromExternalData() throws FileNotFoundException {

        // Boolean to read from JSON or MYSQL true = JSON  false = MYSQL
        boolean readDataType = true;

        if (readDataType)
        {
            File file = new File(filename);
            if (!file.exists())
            {
                System.out.format("ERROR: file %s does not exist.\n", filename);
                System.exit(0);
            }
            InputStream is = new FileInputStream(file);
            JSONTokener tokener = new JSONTokener(is);

            while (true)
            {
                try
                {
                    JSONObject object = new JSONObject(tokener);
                    Profile newProfile = new Profile(object);
                    profiles.add(newProfile);
                } catch (org.json.JSONException e)
                {
                    break;
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            while (true)
            {
                try
                {
                    JSONObject tripObject = new JSONObject(tokener);
                    Trip newTrip = new Trip(tripObject);
                    trips.add(newTrip);
                } catch (org.json.JSONException e)
                {
                    break;
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        } else
            {
            try (Connection connection = DriverManager.getConnection(url, username, password))
            {
                System.out.println("Database connected!");

                System.out.println(" ");


                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("select * from database.user");
                while (rs.next())
                {
                    profiles.add(new Profile(rs.getInt("ID"), rs.getString("userName"), rs.getString("password"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("residence"), rs.getInt("age")));
                }
                ResultSet rs2 = stmt.executeQuery("select * from database.favorite_route");
                while (rs2.next())
                {
                   // favTrips.add(new FavoriteTrip(rs2.getInt("ID"), rs2.getString("locationFrom"), rs2.getString("locationTo")));
                }
                ResultSet rs3 = stmt.executeQuery("select * from database.trip");
                while (rs3.next())
                {
                    trips.add(new Trip(rs3.getInt("ID"), rs3.getString("locationFrom"), rs3.getString("locationTo"), rs3.getString("price"), rs3.getString("distance"), rs3.getString("travelTime"), rs3.getDouble("locationFromLat"), rs3.getDouble("locationFromLng"), rs3.getDouble("locationToLat"), rs3.getDouble("locationToLng")));
                }

                ResultSet rs4 = stmt.executeQuery("select * from database.favorite");
                while (rs4.next())
                {
                   // profiles.get(rs4.getInt("userID")).addFavoriteTrip(favTrips.get(rs4.getInt("routeID")).getID(), favTrips.get(rs4.getInt("routeID")).getLocationFrom(), favTrips.get(rs4.getInt("routeID")).getLocationTo());
                }
            } catch (SQLException e) {
                throw new IllegalStateException("Cannot connect the database!", e);
            }
        }
    }

    // getProfile Method to get index from ArrayList
    public Profile getProfile(int index) {
        return profiles.get(index);
    }

    public ArrayList<Profile> getProfileList() {
        return profiles;
    }

    // getTrip Method to get index from ArrayList
    public Trip getTrip(int index) {
        return trips.get(index);
    }

    public ArrayList<Trip> getTripList() {
        return trips;
    }

}
