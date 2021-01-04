package adsd.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class HomeScreenController
{

    ResourceBundle rb = ResourceBundle.getBundle("lang");

    @FXML
    private TextField locationFrom;
    @FXML
    private TextField locationTo;
    @FXML
    private ChoiceBox timeOption;
    @FXML
    private CheckBox trainBox;
    @FXML
    private CheckBox busBox;
    @FXML
    private CheckBox metroBox;
    @FXML
    private CheckBox carBox;
    @FXML
    private Button checkTripOptions;
    @FXML
    private ChoiceBox<String> vehicleType;

    @FXML private ListView<String> tripOptions;


    // staat hier zodat er geen errors zitten in de html writer etc. moet later wel nog worden aanepast.
    ChoiceBox tripOption;


    DataHandler dataHandler = new DataHandler();
    TripInformationController routeInformationController;


    public void initialize() throws FileNotFoundException
    {

        dataHandler.readFromExternalData();

        locationFrom.setPromptText(rb.getString("HSlocFrom"));
        locationTo.setPromptText(rb.getString("HSlocTo"));

        timeOption.getItems().addAll(  "10:30", "10:35");


        vehicleType.getItems().add(rb.getString("HStrain"));
        vehicleType.getItems().add(rb.getString("HSbus"));
        vehicleType.getItems().add(rb.getString("HSmetro"));

        checkTripOptions.setText(rb.getString("HScheckTripOptionsButton"));


    }

    public String toComma(String timeFormat){
        timeFormat = timeFormat.replace(":", ",");
        return timeFormat;
    }

    public String toDubbleDot(String timeFormat){
        timeFormat = timeFormat.replace(",", ":");
        return timeFormat;
    }



    public void checkTripOptions(ActionEvent event){

        boolean foundMatch = false;

        // search function
        DecimalFormat df = new DecimalFormat("00.00");

        tripOptions.getItems().clear();

        String vertrekplaats = locationFrom.getText();
        String aankomstplaats = locationTo.getText();
        String time = toComma(timeOption.getSelectionModel().getSelectedItem().toString());


        String vehicle = null;
        switch (vehicleType.getSelectionModel().getSelectedIndex()){
            case 0:
                vehicle = "TRANSIT";
                break;
            case 1:
                vehicle = "DRIVING";
                break;
            case 2:
                vehicle = "TRANSIT";
                break;
            default:
                System.out.println("Er is geen keuze gemaakt");
                break;
        }


            // search function
            for (int i = 0; i < dataHandler.getTripList().size(); i++) {
                if (dataHandler.getTrip(i).getLocationFrom().toLowerCase().contains(vertrekplaats.toLowerCase()) &&
                        dataHandler.getTrip(i).getLocationTo().toLowerCase().contains(aankomstplaats.toLowerCase())) {
                    for (int k = 0; k < dataHandler.getTrip(i).getTripTimesList().size(); k++) {
                        if (dataHandler.getTrip(i).getTripTimesList().get(k).getVehicleType().contains(vehicle.toUpperCase()) &&
                                String.valueOf(df.format(dataHandler.getTrip(i).getTripTimesList().get(k).getDepTime())).contains(time)) {


                            tripOptions.getItems().add(dataHandler.getTrip(i).getLocationFrom() + " -> " + dataHandler.getTrip(i).getLocationTo() + " om " + toDubbleDot(df.format(dataHandler.getTrip(i).getTripTimesList().get(k).getDepTime())));
                            System.out.println("Locatie in triplist: " + i + ", triptimelist: " + k);
                            System.out.println(dataHandler.getTrip(i).getLocationFrom() + " naar " + dataHandler.getTrip(i).getLocationTo() + " om " +
                                    df.format(dataHandler.getTrip(i).getTripTimesList().get(k).getDepTime()));
                            foundMatch = true;
                        }

                    }
                }

            }

            if (foundMatch = false){
                // run method again with time selector index +1 and -1



            }


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

                // loads next scene

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