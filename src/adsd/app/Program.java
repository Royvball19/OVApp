package adsd.app;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Program
{

    private static DataHandler datahandler;

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
    {
        datahandler = new DataHandler();
        datahandler.readFromJSON();

        for (int i = 0; i < datahandler.profiles.size(); i++)
        {
            System.out.println(datahandler.getProfile(i).getFirstName() + " " + datahandler.getProfile(i).getLastName());
        }

        System.out.println("\nHuidige routes:");

        for (int i = 0; i < datahandler.trips.size(); i++)
        {
            System.out.println("Van: " + datahandler.getTrip(i).getLocationFrom() + "  Naar: " + datahandler.getTrip(i).getLocationTo());
        }















/*        datahandler.writeToJSON();*/
    }
}
