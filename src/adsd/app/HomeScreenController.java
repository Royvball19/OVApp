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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

public class HomeScreenController
{
    // Get resource bundle
    private ResourceBundle rb = ResourceBundle.getBundle("lang");

    // Attributes FXML
    @FXML private ComboBox locationFrom;
    @FXML private ComboBox locationTo;
    @FXML private Spinner timeSpinnerHour;
    @FXML private Spinner timeSpinnerMin;
    @FXML private Button checkTripOptions;
    @FXML private ComboBox<String> vehicleType;
    @FXML private Label hourLabel;
    @FXML private Label minLabel;
    @FXML private ListView<String> tripOptions;
    @FXML private Button showTripButton;
    @FXML private Label messageLabel;

    // Attributes
    private String lang;
    private String country;
    private Integer resultCount;
    private Integer posA;
    private Integer posB;
    private String locFromInput;
    private String locToInput;
    private double selectedHour;
    private double selectedMin;
    private double selectedTime;

    // Arraylists
    private ArrayList<SearchResult> searchResults = new ArrayList<>();
    private ArrayList<String> locationFromList = new ArrayList<>();
    private ArrayList<String> locationToList = new ArrayList<>();

    // Decimal formatter
    private DecimalFormat df = new DecimalFormat("00.00");

    // New instance of datahandler
    private DataHandler dataHandler = new DataHandler();

    public void initialize() throws IOException
    {

        dataHandler.clearTempData();
        clearInputBoxes();

        dataHandler.readFromExternalData();

        lang = Files.readAllLines(Paths.get("currentLang.txt")).get(0);
        country = Files.readAllLines(Paths.get("currentLang.txt")).get(1);
        Locale.setDefault(new Locale(lang, country));

        ResourceBundle rb = ResourceBundle.getBundle("lang");

        locationFrom.setPromptText(rb.getString("HSlocFrom"));
        locationTo.setPromptText(rb.getString("HSlocTo"));
        vehicleType.setPromptText(rb.getString("HSvehicletype"));

        for (int i = 0; i < dataHandler.getTripList().size(); i++)
        {
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

        for (int i = 0; i < locationFromList.size(); i++)
        {
            locationFrom.getItems().add(locationFromList.get(i));
        }

        for (int i = 0; i < locationToList.size(); i++)
        {
            locationTo.getItems().add(locationToList.get(i));
        }

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
        vehicleType.getItems().add(rb.getString("HSmetro"));
        vehicleType.getItems().add(rb.getString("HStram"));


        checkTripOptions.setText(rb.getString("HScheckTripOptionsButton"));
        showTripButton.setText(rb.getString("HSshowTripInfo"));

    }

    private String toComma(String time)
    {
        time = time.replace(":", ",");
        time = time.replace(".", ",");

        return time;
    }

    public String toDubbleDot(String time)
    {
        time = time.replace(",", ":");
        time = time.replace(".", ":");

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

        // clear possible trips and reset result count
        tripOptions.getItems().clear();
        searchResults.clear();
        resultCount = 0;
        messageLabel.setVisible(false);

        // check if input is given
        if (locationFrom.getSelectionModel().getSelectedIndex() == -1
                | locationTo.getSelectionModel().getSelectedIndex() == -1
                | vehicleType.getSelectionModel().getSelectedIndex() == -1

        )
        // set message for empty fields
        {
            locationFrom.setPromptText(rb.getString("HSpromptFrom"));
            locationTo.setPromptText(rb.getString("HSpromptTo"));
            vehicleType.setPromptText(rb.getString("HSpromptVehicle"));

        } else if (locationTo.getSelectionModel().getSelectedItem().toString().equals(locationFrom.getSelectionModel().getSelectedItem().toString()))
        {
            messageLabel.setVisible(true);
            messageLabel.setText(rb.getString("HSmessageDubble"));
        } else
        {
            setInput();

            do
            // loop searchTrip function until three results are given
            {
                String time = toComma(df.format(selectedTime));
                String vehicle = switch (vehicleType.getSelectionModel().getSelectedIndex())
                        {
                            case 0 -> "trein";
                            case 1 -> "bus";
                            case 2 -> "metro";
                            case 3 -> "tram";
                            default -> null;
                        };
                searchTrip(locFromInput, locToInput, time, vehicle);

                selectedTime += 0.01;

                if (selectedTime > 24.00)
                {
                    selectedTime = 0.0;
                }


            } while (resultCount < 3);

            tripOptions.setVisible(true);
            showTripButton.setVisible(true);

        }

        if (resultCount == 0)
        {
            tripOptions.getItems().add(rb.getString("HSresult"));
            tripOptions.setVisible(true);
        }


    }

    private void setInput()
    {
        locFromInput = locationFrom.getSelectionModel().getSelectedItem().toString();
        locToInput = locationTo.getSelectionModel().getSelectedItem().toString();
        selectedHour = Double.parseDouble(timeSpinnerHour.getValue().toString());
        selectedMin = Double.parseDouble(timeSpinnerMin.getValue().toString()) / 100;
        selectedTime = selectedHour + selectedMin;
    }


    private void searchTrip(String locFrom, String locTo, String time, String vehicle) throws IOException
    {
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
                        if (dataHandler.getTrip(i).getTripTimesList().get(k).getVehicleType().contains(vehicle)
                                &&
                                toComma(String.valueOf(df.format(dataHandler.getTrip(i).getTripTimesList().get(k).getDepTime()))).contains(time))
                        {
                            vehicle = dataHandler.getTrip(i).getTripTimesList().get(k).getVehicleType();
                            tripOptions.getItems().add(dataHandler.getTrip(i).getLocationFrom() + " -> " + dataHandler.getTrip(i).getLocationTo() + " om " + toDubbleDot(df.format(dataHandler.getTrip(i).getTripTimesList().get(k).getDepTime())));
//
                            String title = dataHandler.getTrip(i).getLocationFrom() + " -> " + dataHandler.getTrip(i).getLocationTo() + " om " + toDubbleDot(df.format(dataHandler.getTrip(i).getTripTimesList().get(k).getDepTime()));
                            SearchResult result = new SearchResult(title, i, k);
                            searchResults.add(result);
                            resultCount++;
                        }

                    } else
                    {
                        // NL method
                        if (dataHandler.getTrip(i).getTripTimesList().get(k).getVehicleType().contains(vehicle)
                                &&
                                String.valueOf(df.format(dataHandler.getTrip(i).getTripTimesList().get(k).getDepTime())).contains(time))
                        {
                            vehicle = dataHandler.getTrip(i).getTripTimesList().get(k).getVehicleType();
                            tripOptions.getItems().add(dataHandler.getTrip(i).getLocationFrom() + " -> " + dataHandler.getTrip(i).getLocationTo() + " om " + toDubbleDot(df.format(dataHandler.getTrip(i).getTripTimesList().get(k).getDepTime())));

                            String title = dataHandler.getTrip(i).getLocationFrom() + " -> " + dataHandler.getTrip(i).getLocationTo() + " om " + toDubbleDot(df.format(dataHandler.getTrip(i).getTripTimesList().get(k).getDepTime()));
                            SearchResult result = new SearchResult(title, i, k);
                            searchResults.add(result);
                            resultCount++;
                        }
                    }
                }
            }
        }
    }


    public void showTrip(ActionEvent event) throws IOException
    {

        if (tripOptions.getSelectionModel().getSelectedIndex() == -1)
        {
            messageLabel.setVisible(true);
            messageLabel.setText(rb.getString("HSmessage"));
        } else
        {

            tripOptions.getSelectionModel().getSelectedIndex();
            posA = searchResults.get(tripOptions.getSelectionModel().getSelectedIndex()).getPosA();
            posB = searchResults.get(tripOptions.getSelectionModel().getSelectedIndex()).getPosB();

            String mapType = switch (dataHandler.getTrip(posA).getTripTimesList().get(posB).getVehicleType())
                    {
                        case "trein" -> "TRANSIT";
                        case "bus" -> "DRIVING";
                        case "metro" -> "TRANSIT";
                        case "tram" -> "DRIVING";
                        default -> "geen";
                    };

            File f = new File("src/adsd/app/index.html");
//        File f = new File("src/adsd/app/index.html");
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
                        "                origin: { lat: " + dataHandler.getTrip(posA).getLocationFromLat() + ", lng: " + dataHandler.getTrip(posA).getLocationFromLng() + " },\n" +
                        "                destination: { lat: " + dataHandler.getTrip(posA).getLocationToLat() + ", lng: " + dataHandler.getTrip(posA).getLocationToLng() + " },\n" +
                        "                // Note that Javascript allows us to access the constant\n" +
                        "                // using square brackets and a string value as its\n" +
                        "                // \"property.\"\n" +
                        "                travelMode: '" + mapType + "',\n" +
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

            } catch (IOException e)
            {
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
    }

    public void showMyProfileButton(ActionEvent event) throws IOException
    {

        File myObj = new File("currentuser.txt");
        if (myObj.length() == 0)
        {
            Parent parent = FXMLLoader.load(getClass().getResource("fxml/LoginScreen.fxml"));
            Scene myProfileScene = new Scene(parent);

            myProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

            // This line gets the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(myProfileScene);
            window.show();
        } else
        {
            Parent parent = FXMLLoader.load(getClass().getResource("fxml/MyProfile.fxml"));
            Scene myProfileScene = new Scene(parent);

            myProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

            // This line gets the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(myProfileScene);
            window.show();
        }
    }

    public void showMyFavoriteTrips(ActionEvent event) throws IOException
    {
        File myObj = new File("currentuser.txt");
        if (myObj.length() == 0)
        {
            Parent loginScreenParent = FXMLLoader.load(getClass().getResource("fxml/LoginScreen.fxml"));
            Scene myProfileScene = new Scene(loginScreenParent);

            myProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

            // This line gets the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(myProfileScene);
            window.show();
        } else
        {
            Parent favTripParent = FXMLLoader.load(getClass().getResource("fxml/MyFavoriteTrips.fxml"));
            Scene myProfileScene = new Scene(favTripParent);

            myProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

            // This line gets the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(myProfileScene);
            window.show();
        }
    }

    public void changeLangEng(ActionEvent event) throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get("currentLang.txt"));
        lines.set(0, "en");
        lines.set(1, "US");
        Files.write(Paths.get("currentLang.txt"), lines); // You can add a charset and other options too

        initialize();

    }

    public void changeLangNed(ActionEvent event) throws IOException
    {

        List<String> lines = Files.readAllLines(Paths.get("currentLang.txt"));
        lines.set(0, "nl");
        lines.set(1, "NL");
        Files.write(Paths.get("currentLang.txt"), lines); // You can add a charset and other options too

        initialize();

    }

    public void planFavTrip(String from, String to)
    {

        locationFrom.getSelectionModel().select(from);
        locationTo.getSelectionModel().select(to);

    }
}