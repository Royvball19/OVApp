package adsd.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;

import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.MalformedURLException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

public class HomeScreenController {

    ResourceBundle rb = ResourceBundle.getBundle("lang");

    @FXML
    private ComboBox locationFrom;
    @FXML
    private ComboBox locationTo;
    @FXML
    private Spinner timeSpinnerHour;
    @FXML
    private Spinner timeSpinnerMin;
    @FXML
    private Button checkTripOptions;
    @FXML
    private ComboBox<String> vehicleType;
    @FXML
    private Label hourLabel;
    @FXML
    private Label minLabel;
    @FXML
    private ListView<String> tripOptions;
    @FXML
    private Button showTripButton;

    private String lang;
    private String country;

    // staat hier zodat er geen errors zitten in de html writer etc. moet later wel nog worden aangepast.
    ChoiceBox tripOption;
    private Integer resultCount;
    private Integer posA;
    private Integer posB;
    private String vehicle;
    private ArrayList<SearchResult> searchResults = new ArrayList<>();
    private ArrayList<String> locationFromList = new ArrayList<>();
    private ArrayList<String> locationToList = new ArrayList<>();




    DataHandler dataHandler = new DataHandler();

    public void initialize() throws IOException {

        ResourceBundle rb = ResourceBundle.getBundle("lang");

        clearInputBoxes();
        dataHandler.readFromExternalData();

        lang = Files.readAllLines(Paths.get("currentLang.txt")).get(0);
        country = Files.readAllLines(Paths.get("currentLang.txt")).get(1);
        Locale.setDefault(new Locale(lang, country));


        locationFrom.setPromptText(rb.getString("HSlocFrom"));
        locationTo.setPromptText(rb.getString("HSlocTo"));
        vehicleType.setPromptText(rb.getString("HSvehicletype"));

        for (int i = 0; i < dataHandler.getTripList().size(); i++) {
            locationFromList.add(dataHandler.getTrip(i).getLocationFrom());
            locationToList.add(dataHandler.getTrip(i).getLocationTo());
        }

        //The Hashset makes sure there are no duplicate locations
        Set<String> set = new HashSet<>(locationFromList);
        locationFromList.clear();
        locationFromList.addAll(set);

        Set<String> set2 = new HashSet<>(locationToList);
        locationToList.clear();
        locationToList.addAll(set2);

        for (int i = 0; i < locationFromList.size(); i++) {
            locationFrom.getItems().add(locationFromList.get(i));
        }

        for (int i = 0; i < locationToList.size(); i++) {
            locationTo.getItems().add(locationToList.get(i));
        }

        //timeOption.getItems().addAll(  "10:30", "10:35");

        SpinnerValueFactory<Integer> hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24);
        this.timeSpinnerHour.setValueFactory(hourValueFactory);
        hourValueFactory.setWrapAround(true);
        timeSpinnerHour.setEditable(true);

        SpinnerValueFactory<Integer> minValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        this.timeSpinnerMin.setValueFactory(minValueFactory);
        minValueFactory.setWrapAround(true);
        timeSpinnerMin.setEditable(true);

        hourLabel.setText(rb.getString("HShour"));
        minLabel.setText(rb.getString("HSminute"));

        vehicleType.getItems().add(rb.getString("HStrain"));
        vehicleType.getItems().add(rb.getString("HSbus"));


        checkTripOptions.setText(rb.getString("HScheckTripOptionsButton"));
        showTripButton.setText(rb.getString("HSshowTripInfo"));


    }

    private String toComma(String time) {
        time = time.replace(":", ",");
        time = time.replace(".", ",");

        return time;
    }

    public String toDubbleDot(String time) {
        time = time.replace(",", ":");
        time = time.replace(".", ":");

        return time;
    }

    private String toDot(String time) {
        time = time.replace(",", ".");

        return time;
    }

    private void clearInputBoxes()
    {
        locationFrom.getItems().clear();
        locationTo.getItems().clear();
        vehicleType.getItems().clear();

    }

    public void checkTripOptions(ActionEvent event) throws IOException
    {

        tripOptions.getItems().clear();
        searchResults.clear();
        resultCount = 0;



        if (locationFrom.getSelectionModel().getSelectedIndex() == -1
                | locationTo.getSelectionModel().getSelectedIndex() == -1
                | vehicleType.getSelectionModel().getSelectedIndex() == -1)
        {
            locationFrom.setPromptText(rb.getString("HSpromptFrom"));
            locationTo.setPromptText(rb.getString("HSpromptTo"));
            vehicleType.setPromptText(rb.getString("HSpromptVehicle"));

        } else {

            String locFrom = locationFrom.getSelectionModel().getSelectedItem().toString();
            String locTo = locationTo.getSelectionModel().getSelectedItem().toString();

            double selectedHour = Double.parseDouble(timeSpinnerHour.getValue().toString());
            double selectedMin = Double.parseDouble(timeSpinnerMin.getValue().toString()) / 100;

            double selectedTime = selectedHour + selectedMin;

            String time = toComma(Double.toString(selectedTime));

            String vehicle = null;
            switch (vehicleType.getSelectionModel().getSelectedIndex()) {
                case 0:
                    vehicle = "TRANSIT";
                    break;
                case 1:
                    vehicle = "DRIVING";
                    break;

            }

            time = "10,3";
            System.out.println("tijd is: " + time);


            System.out.println("location from: " + locFrom);
            System.out.println("Location to: " + locTo);
            System.out.println("time: " + time);
            System.out.println("vehicle: " + vehicle);
            System.out.println(vehicleType.getSelectionModel().getSelectedIndex());

            searchTrip(locFrom, locTo, time, vehicle);


            tripOptions.setVisible(true);
            showTripButton.setVisible(true);

        }

        if (resultCount == 0) {
            tripOptions.getItems().add(rb.getString("HSresult"));
            tripOptions.setVisible(true);
        }




    }




    private void searchTrip(String locFrom, String locTo, String time, String vehicle) throws IOException
    {
        boolean foundMatch = false;
        DecimalFormat df = new DecimalFormat("00.00");

        lang = Files.readAllLines(Paths.get("currentLang.txt")).get(0);


        for (int i = 0; i < dataHandler.getTripList().size(); i++)
        {

            if (dataHandler.getTrip(i).getLocationFrom().toLowerCase().contains(locFrom.toLowerCase())
                    &&
                    dataHandler.getTrip(i).getLocationTo().toLowerCase().contains(locTo.toLowerCase()))
            {

                for (int k = 0; k < dataHandler.getTrip(i).getTripTimesList().size(); k++)
                {

                    if (lang.equals("en"))
                    {

                        // ENG Method
                        if (dataHandler.getTrip(i).getTripTimesList().get(k).getVehicleType().contains(vehicle.toUpperCase())
                                &&
                                toComma(String.valueOf(df.format(dataHandler.getTrip(i).getTripTimesList().get(k).getDepTime()))).contains(time))
                        {
                            vehicle = dataHandler.getTrip(i).getTripTimesList().get(k).getVehicleType();
                            tripOptions.getItems().add(dataHandler.getTrip(i).getLocationFrom() + " -> " + dataHandler.getTrip(i).getLocationTo() + " om " + toDubbleDot(df.format(dataHandler.getTrip(i).getTripTimesList().get(k).getDepTime())));
                            System.out.println("Locatie in triplist: " + i + ", triptimelist: " + k);

                            String title = dataHandler.getTrip(i).getLocationFrom() + " -> " + dataHandler.getTrip(i).getLocationTo() + " om " + toDubbleDot(df.format(dataHandler.getTrip(i).getTripTimesList().get(k).getDepTime()));
                            SearchResult result = new SearchResult(title, i, k);


                            searchResults.add(result);


                            System.out.println(posA + " " + posB);
                            resultCount++;
                        }
                        } else
                        {
                            // NL method
                            if (dataHandler.getTrip(i).getTripTimesList().get(k).getVehicleType().contains(vehicle.toUpperCase())
                                    &&
                                    String.valueOf(df.format(dataHandler.getTrip(i).getTripTimesList().get(k).getDepTime())).contains(time))
                            {
                                vehicle = dataHandler.getTrip(i).getTripTimesList().get(k).getVehicleType();
                                tripOptions.getItems().add(dataHandler.getTrip(i).getLocationFrom() + " -> " + dataHandler.getTrip(i).getLocationTo() + " om " + toDubbleDot(df.format(dataHandler.getTrip(i).getTripTimesList().get(k).getDepTime())));
                                System.out.println("Locatie in triplist: " + i + ", triptimelist: " + k);

                                String title = dataHandler.getTrip(i).getLocationFrom() + " -> " + dataHandler.getTrip(i).getLocationTo() + " om " + toDubbleDot(df.format(dataHandler.getTrip(i).getTripTimesList().get(k).getDepTime()));
                                SearchResult result = new SearchResult(title, i, k);


                                searchResults.add(result);


                                System.out.println(posA + " " + posB);
                                resultCount++;
                            }
                        }
                    }
                }
            }
    }



    public void showTrip(ActionEvent event) throws IOException {

        tripOptions.getSelectionModel().getSelectedIndex();
        posA = searchResults.get(tripOptions.getSelectionModel().getSelectedIndex()).getPosA();
        posB = searchResults.get(tripOptions.getSelectionModel().getSelectedIndex()).getPosB();


        File f = new File("src/adsd/app/index.html");
//        File f = new File("src/adsd/app/index.html");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {

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
                    "                origin: { lat: " + dataHandler.getTrip(posA).getLocationFromLat() + ", lng: " + dataHandler.getTrip(posA).getLocationFromLng() + " },\n" +
                    "                destination: { lat: " + dataHandler.getTrip(posA).getLocationToLat() + ", lng: " + dataHandler.getTrip(posA).getLocationToLng() + " },\n" +
                    "                // Note that Javascript allows us to access the constant\n" +
                    "                // using square brackets and a string value as its\n" +
                    "                // \"property.\"\n" +
                    "                travelMode: '" + dataHandler.getTrip(posA).getTripTimesList().get(posB).getVehicleType() + "',\n" +
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

            bw.write("");
            bw.write("");
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // loads next scene

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxml/TripInformation.fxml"));
        Parent tripInfoParent = loader.load();

        Scene tripInfoScene = new Scene(tripInfoParent);
        // this loads the correct text into labels
        tripInfoScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());


        TripInformationController TripControl = loader.getController();
        TripControl.sendInput(posA, posB);

        // This line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tripInfoScene);
        window.show();


    }

    public void showMyProfileButton(ActionEvent event) throws IOException {
        Parent homeScreenParent2 = FXMLLoader.load(getClass().getResource("fxml/MyProfile.fxml"));
        Scene myProfileScene = new Scene(homeScreenParent2);

        myProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // This line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(myProfileScene);
        window.show();
    }

    public void showMyFavoriteTrips(ActionEvent event) throws IOException {
        Parent homeScreenParent3 = FXMLLoader.load(getClass().getResource("fxml/MyFavoriteTrips.fxml"));
        Scene myFavoriteTrips = new Scene(homeScreenParent3);

        myFavoriteTrips.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // This line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(myFavoriteTrips);
        window.show();
    }

    public void changeLangEng(ActionEvent event) throws IOException
    {
//        List<String> lines = Files.readAllLines(Paths.get("currentLang.txt"));
//        lines.set(0, "en");
//        lines.set(1, "US");
//        Files.write(Paths.get("currentLang.txt"), lines); // You can add a charset and other options too
//
//        initialize();

    }

    public void changeLangNed(ActionEvent event) throws IOException {

//        List<String> lines = Files.readAllLines(Paths.get("currentLang.txt"));
//        lines.set(0, "nl");
//        lines.set(1, "NL");
//        Files.write(Paths.get("currentLang.txt"), lines); // You can add a charset and other options too
//
//        initialize();

    }
}