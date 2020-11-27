package adsd.app;

import javafx.application.Application;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Program
{

    private static DataHandler datahandler;
    private static ResourceBundle rb = ResourceBundle.getBundle("lang");

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
    {
        // Datahandler import
        datahandler = new DataHandler();
        // Datahandler readFromJSON always at the start of the application
        datahandler.readFromJSON();


        Scanner scan = new Scanner(System.in);
        boolean next = true;


        while (next) {
            System.out.println(rb.getString("choice1"));
            System.out.println("[1] Amersfoort -> Utrecht");
            System.out.println("[2] Rotterdam -> Amsterdam");
            System.out.println("[3] Groningen -> Limburg");

            String keuze = scan.nextLine();

            switch(keuze){

                case "0":
                    next = false;

                case "1":
                    datahandler.getTrip(0).printTripDetails();

                    break;

                case "2":
                    datahandler.getTrip(1).printTripDetails();
                    break;

                case "3":
                    datahandler.getTrip(2).printTripDetails();
                    break;

                default:
                    System.out.println("Voer een geldige keuze in!");
                    break;
            }
        }


        // Datahandler writeToJSON always at the end of the application
        datahandler.writeToJSON();

    }
}
