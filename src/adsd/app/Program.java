package adsd.app;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Program
{

    private static DataHandler datahandler;

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
    {
        // Datahandler import
        datahandler = new DataHandler();
        // Datahandler readFromJSON always at the start of the application
        datahandler.readFromJSON();

        ResourceBundle rb = ResourceBundle.getBundle("lang");

        Scanner scan = new Scanner(System.in);
        boolean next = true;

        while (next) {
            System.out.println(rb.getString("choice1"));
            System.out.println("[1] Amersfoort -> Utrecht");
            System.out.println("[2] Rotterdam -> Amsterdam");
            System.out.println("[3] Groningen -> Limburg");

            int keuze = scan.nextInt();

            switch(keuze){

                case 0:
                    next = false;

                case 1:
                    datahandler.getTrip(0).printTripDetails();

                    next = true;
                    break;

                case 2:
                    datahandler.getTrip(1).printTripDetails();
                    next = true;
                    break;

                case 3:
                    datahandler.getTrip(2).printTripDetails();
                    next = true;
                    break;

                default:
                    break;
            }
        }

        // Datahandler writeToJSON always at the end of the application
        datahandler.writeToJSON();

    }
}
