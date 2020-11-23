package adsd.app;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Program
{

    private static DataHandler datahandler;

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
    {
        // Datahandler import
        datahandler = new DataHandler();
        // Datahandler readFromJSON always at the start of the application
        datahandler.readFromJSON();


        // Datahandler writeToJSON always at the end of the application
        datahandler.writeToJSON();
    }
}
