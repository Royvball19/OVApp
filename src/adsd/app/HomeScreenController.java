package adsd.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ResourceBundle;

public class HomeScreenController
{

    ResourceBundle rb = ResourceBundle.getBundle("lang");

    @FXML
    private Label departureRoute;
    @FXML
    private Label departureTime;

    @FXML
    private ChoiceBox tripOption;
    @FXML
    private ChoiceBox timeOption;
    @FXML
    private ChoiceBox dateOption;
    @FXML
    private Button planTripButton;


    DataHandler dataHandler;
    TripInformationController routeInformationController;


    public void initialize() throws FileNotFoundException
    {

        dataHandler = new DataHandler();
        dataHandler.readFromExternalData();

        departureRoute.setText((rb.getString("departureRoute")));
        departureRoute.setStyle("-fx-font-weight: bold");


        departureTime.setText((rb.getString("departureTime")));
        departureTime.setStyle("-fx-font-weight: bold");

        planTripButton.setText((rb.getString("planTripButton")));

        for (int i = 0; i < dataHandler.getTripList().size(); i++)
        {
            tripOption.getItems().add(dataHandler.getTrip(i).getLocationFrom() + " -> " + dataHandler.getTrip(i).getLocationTo());
        }

        timeOption.getItems().addAll(rb.getString("choicebox"), "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
        timeOption.getSelectionModel().select(0);
        dateOption.getItems().addAll(rb.getString("choiceboxdate"), "Vandaag", "Morgen", "Overmorgen");
        dateOption.getSelectionModel().select(0);


    }




    public void showTrip(ActionEvent event) throws IOException
    {

        File f = new File("C:/Users/royva/IdeaProjects/OVApp/src/adsd/app/index.html");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f)))
        {

            //Writer to HTML to change the Locations and Vehicletype
            bw.write("<!DOCTYPE html> \n" +
                    "<html> \n" +
                    "<head> \n" +
                    "<script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyBIwzALxUPNbatRBj3Xi1Uhp0fFzwWNBkE&callback=initMap&libraries=&v=weekly\" defer></script>\n" +
                    "    <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" /> \n" +
                    "    <script>\n" +
                    "        function initMap() {\n" +
                    "        const directionsRenderer = new google.maps.DirectionsRenderer();\n" +
                    "        const directionsService = new google.maps.DirectionsService();\n" +
                    "        const map = new google.maps.Map(document.getElementById(\"map\"), {\n" +
                    "            mapTypeControl: false,\n" +
                    "            streetViewControl: false,\n" +
                    "            zoom: 14,\n" +
                    "            restriction: {\n" +
                    "                latLngBounds: {\n" +
                    "                    north: 53.79305,\n" +
                    "                    south: 50.66916,\n" +
                    "                    east: 7.45822,\n" +
                    "                    west: 2.88242,\n" +
                    "                },\n" +
                    "            },\n" +
                    "        });\n" +
                    "        directionsRenderer.setMap(map);\n" +
                    "        calculateAndDisplayRoute(directionsService, directionsRenderer);\n" +
                    "    }\n" +
                    "\n" +
                    "    function calculateAndDisplayRoute(directionsService, directionsRenderer) {\n" +
                    "        directionsService.route(\n" +
                    "            {\n" +
                    "                origin: { lat: " + dataHandler.getTrip(tripOption.getSelectionModel().getSelectedIndex()).getLocationFromLat() + ", lng: " + dataHandler.getTrip(tripOption.getSelectionModel().getSelectedIndex()).getLocationFromLng() + " },\n" +
                    "                destination: { lat: " + dataHandler.getTrip(tripOption.getSelectionModel().getSelectedIndex()).getLocationToLat() + ", lng: " + dataHandler.getTrip(tripOption.getSelectionModel().getSelectedIndex()).getLocationToLng() + " },\n" +
                    "                // Note that Javascript allows us to access the constant\n" +
                    "                // using square brackets and a string value as its\n" +
                    "                // \"property.\"\n" +
                    "                travelMode: '" + dataHandler.getTrip(tripOption.getSelectionModel().getSelectedIndex()).getTripTimesList().get(0).getVehicleType() + "',\n" +
                    "            },\n" +
                    "            (response, status) => {\n" +
                    "                if (status == \"OK\") {\n" +
                    "                    directionsRenderer.setDirections(response);\n" +
                    "                } else {\n" +
                    "                    window.alert(\"Directions request failed due to \" + status);\n" +
                    "                }\n" +
                    "            }\n" +
                    "        );\n" +
                    "        }\n" +
                    "    </script>\n" +
                    "</head>\n" +
                    "<body>\n" +
    /*                "<div id=\"floating-panel\">\n" +
                    "    <b>Van: </b>\n" +
                    "    <select id=\"start\">\n" +
                    "        <option value=\"Utrecht Centraal\">Utrecht</option>\n" +
                    "    </select>\n" +
                    "    <b>Naar: </b>\n" +
                    "    <select id=\"end\">\n" +
                    "        <option value=\"Amersfoort Centraal\">Amersfoort</option>\n" +
                    "    </select>\n" +
                    "</div>\n" +*/
                    "<div id=\"map\"></div>\n" +
                    "</body>\n" +
                    "</html>");

            bw.write(""); bw.write(""); bw.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }


        if (tripOption.getSelectionModel().getSelectedIndex() == -1)
        {
            // set text to a box with message: "please select"
        }
        else
            {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("TripInformation.fxml"));
            Parent tripInfoParent = loader.load();

            Scene tripInfoScene = new Scene(tripInfoParent);
            // this loads the correct text into labels
            tripInfoScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());


            TripInformationController TripControl = loader.getController();
            TripControl.sendInput(tripOption.getSelectionModel().getSelectedIndex());

            // This line gets the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(tripInfoScene);
            window.show();


        }

    }

    public void showMyProfileButton(ActionEvent event) throws IOException
    {
        Parent homeScreenParent2 = FXMLLoader.load(getClass().getResource("MyProfile.fxml"));
        Scene myProfileScene = new Scene(homeScreenParent2);

        myProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // This line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(myProfileScene);
        window.show();
    }

    public void showMyFavoriteTrips(ActionEvent event) throws IOException
    {
        Parent homeScreenParent3 = FXMLLoader.load(getClass().getResource("MyFavoriteTrips.fxml"));
        Scene myFavoriteTrips = new Scene(homeScreenParent3);

        myFavoriteTrips.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // This line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(myFavoriteTrips);
        window.show();
    }


}