package adsd.app;


import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class GUI extends Application
{
    private Scene chooseTripScene, viewTripDetails, viewMyProfile;
    private DataHandler dataHandler;
    private Scanner in        = new Scanner(System.in);


    @Override
    public void start(Stage primaryStage) throws IOException {

        dataHandler = new DataHandler();
        dataHandler.readFromExternalData();

        Locale.setDefault(new Locale("nl", "NL"));
        ResourceBundle rb = ResourceBundle.getBundle("lang");

        // GUI Labels
        Label departureRoute = new Label(rb.getString("departureRoute"));
        departureRoute.setId("departureRouteText");

        Label departureTime = new Label(rb.getString("departureTime"));
        departureTime.setId("departureTimeText");
        departureTime.setVisible(false);

        Label showTripInformation = new Label(rb.getString("showTripInformation"));
        showTripInformation.setStyle("-fx-font-weight: bold");

        Label records = new Label(rb.getString("records"));
        records.setStyle("-fx-font-weight: bold");

//        Label profileRecords = new Label("Naam: " + dataHandler.getProfile(0).getFirstName() + " " + dataHandler.getProfile(0).getLastName());

        Label locationFrom = new Label();

        Label locationTo = new Label();

        Label tripTime = new Label();

        Label tripPrice = new Label();

        Label labelFirstNameInfo = new Label();
        labelFirstNameInfo.setText(rb.getString("labelFirstNameInfo"));

        Label labelLastNameInfo = new Label();
        labelLastNameInfo.setText(rb.getString("labelLastNameInfo"));


        Label labelFirstNameInfoRecords = new Label();

        Label labelLastNameInfoRecords = new Label();


        // Buttons and clickable items
        ToolBar toolBar = new ToolBar();
        toolBar.setId("toolbar");

        Button planTripButton = new Button(rb.getString("planTripButton"));
        planTripButton.setId("Testbutton");

        Button chooseOtherTrip = new Button(rb.getString("chooseOtherTrip"));

        Button addToFavorite = new Button(rb.getString("addToFavorite"));

        Button showMapButton = new Button(rb.getString("showMapButton"));

        Button myProfileButton = new Button();
        toolBar.getItems().add(myProfileButton);
        myProfileButton.setId("myProfileButton");

/*        Button recentTripButton = new Button();
        toolBar.getItems().add(recentTripButton);
        recentTripButton.setId("RecentTripButton");*/

        Button myFavoriteButton = new Button();
        toolBar.getItems().add(myFavoriteButton);
        myFavoriteButton.setId("myFavoriteButton");

        Button chooseLangButton = new Button();
        toolBar.getItems().add(chooseLangButton);
        chooseLangButton.setId("langOptionButton");

        Button showMapReturnButton = new Button();
        showMapReturnButton.setId("showMapReturnButton");
        showMapReturnButton.setLayoutX(10);
        showMapReturnButton.setLayoutY(350);

        Button returnToHome = new Button();
        returnToHome.setText("Terug");



        // Choice boxes
        ChoiceBox choicebox = new ChoiceBox();
        choicebox.getItems().addAll(rb.getString("choicebox"), "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
        choicebox.getSelectionModel().select(0);
        choicebox.setVisible(true);

        ChoiceBox tripOption = new ChoiceBox();
        tripOption.getItems().addAll();
        choicebox.getSelectionModel().select(0);
        choicebox.setVisible(true);

        ChoiceBox profileSelector = new ChoiceBox();

        ChoiceBox<String> tripDateOption = new ChoiceBox<>();
        tripDateOption.getItems().addAll(rb.getString("choiceboxdate"), "Vandaag", "Morgen", "Overmorgen");
        tripDateOption.getSelectionModel().select(0);
        tripDateOption.setVisible(true);


        // Loop to add trips to tripOption choicebox
        for (int i = 0; i < dataHandler.getTripList().size(); i++ )
        {
            tripOption.getItems().add(dataHandler.getTrip(i).getLocationFrom() + " -> " + dataHandler.getTrip(i).getLocationTo());
        }

        for (int i = 0; i < dataHandler.getProfileList().size(); i++ )
        {
            profileSelector.getItems().add(dataHandler.getProfile(i).getFirstName());
        }


        // Listener for selecting the trip
        tripOption.getSelectionModel().selectedIndexProperty().addListener((v, oldValue, newValue) ->
        {
            locationFrom.setText(rb.getString("locationFrom") + dataHandler.getTrip((Integer) newValue).getLocationFrom());
            locationTo.setText(rb.getString("locationTo") + dataHandler.getTrip((Integer) newValue).getLocationTo());
/*            tripTime.setText(rb.getString("tripTime") + dataHandler.getTrip((Integer) newValue).getTravelTime());
            tripPrice.setText(rb.getString("tripPrice") + dataHandler.getTrip((Integer) newValue).getVehicle());*/

            File f = new File("C:/Users/royva/IdeaProjects/OVApp/src/adsd/app/index.html");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {

                //Writer to HTML to change the locations
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
                        "                origin: { lat: " + dataHandler.getTrip((Integer) newValue).getLocationFromLat() + ", lng: " + dataHandler.getTrip((Integer) newValue).getLocationFromLng() + " },\n" +
                        "                destination: { lat: " + dataHandler.getTrip((Integer) newValue).getLocationToLat() + ", lng: " + dataHandler.getTrip((Integer) newValue).getLocationToLng() + " },\n" +
                        "                // Note that Javascript allows us to access the constant\n" +
                        "                // using square brackets and a string value as its\n" +
                        "                // \"property.\"\n" +
                        "                travelMode: 'TRANSIT',\n" +
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        profileSelector.getSelectionModel().selectedIndexProperty().addListener((v, oldValue, newValue) ->
        {
            labelFirstNameInfoRecords.setText(dataHandler.getProfile((Integer) newValue).getFirstName());
            labelLastNameInfoRecords.setText(dataHandler.getProfile((Integer) newValue).getLastName());
        });

        showMapButton.setOnAction(e ->
        {


            Pane pane = new Pane();
            pane.setId("showMapPane");
            Scene showMapScene = new Scene(pane, 255, 400);
            primaryStage.setResizable(false);

            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();

            //Change the pathname to ur own location of the index.html
            String url = "file:///C:/Users/royva/IdeaProjects/OVApp/src/adsd/app/index.html";
            webEngine.load(url);
            pane.getChildren().add(webView);
            pane.getChildren().add(showMapReturnButton);
            pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());


            primaryStage.setScene(showMapScene);
        });

        showMapReturnButton.setOnAction(e ->
        {
            primaryStage.setScene(viewTripDetails);
        });


        // Button action listeners
        planTripButton.setOnAction(e ->
        {
            if (tripOption.getSelectionModel().getSelectedIndex() == -1) {
                System.out.println("Please select");
            }
                else{
                    primaryStage.setScene(viewTripDetails);
                }
        });

        chooseOtherTrip.setOnAction(e ->
        {
            primaryStage.setScene(chooseTripScene);
        });

        myProfileButton.setOnAction(e ->
        {
            primaryStage.setScene(viewMyProfile);
        });

        returnToHome.setOnAction(e ->
        {
            primaryStage.setScene(chooseTripScene);
        });


        // Layout ChooseTripScene (FirstScene)
        GridPane chooseTripLayout = new GridPane();
        chooseTripLayout.setId("chooseTripLayout");
        chooseTripLayout.setHgap(10);
        chooseTripLayout.setVgap(12);
        chooseTripLayout.add(toolBar, 5, 0);
        chooseTripLayout.add(tripOption, 5, 5);
        chooseTripLayout.add(departureRoute, 5, 4);
        chooseTripLayout.add(departureTime, 5, 7);
        chooseTripLayout.add(choicebox, 5, 8);
        chooseTripLayout.add(tripDateOption, 5, 9);
/*        chooseTripLayout.add(recentTripButton, 4, 10);*/
        chooseTripLayout.add(planTripButton, 5, 12);
        chooseTripLayout.setHalignment(tripDateOption, HPos.CENTER);
        chooseTripLayout.setHalignment(departureTime, HPos.CENTER);
        chooseTripLayout.setHalignment(departureRoute, HPos.CENTER);
        chooseTripLayout.setHalignment(tripOption, HPos.CENTER);
        chooseTripLayout.setHalignment(choicebox, HPos.CENTER);
        chooseTripLayout.setHalignment(planTripButton, HPos.CENTER);
        chooseTripScene = new Scene(chooseTripLayout, 255, 400);

        // Layout viewTripLayout (SecondScene)
        GridPane viewTripLayout = new GridPane();
        viewTripLayout.setId("viewTripLayout");
        viewTripLayout.setHgap(10);
        viewTripLayout.setVgap(12);
        viewTripLayout.add(showTripInformation, 5, 2);
        viewTripLayout.add(locationFrom, 5, 4);
        viewTripLayout.add(tripTime, 5, 5);
        viewTripLayout.add(locationTo, 5, 6);
        viewTripLayout.add(tripPrice, 5, 7);
        viewTripLayout.add(chooseOtherTrip, 5, 10);
        viewTripLayout.add(addToFavorite, 5, 11);
        viewTripLayout.add(showMapButton, 5, 14);
        viewTripLayout.setHalignment(showTripInformation, HPos.CENTER);
        viewTripLayout.setHalignment(chooseOtherTrip, HPos.CENTER);
        viewTripLayout.setHalignment(addToFavorite, HPos.CENTER);
        viewTripLayout.setHalignment(showMapButton, HPos.CENTER);
        viewTripDetails = new Scene(viewTripLayout, 255, 400);

        // Layout viewProfileLayout (ThirdScene)
        GridPane viewProfileLayout = new GridPane();
        viewProfileLayout.setId("viewProfileLayout");
        viewProfileLayout.setHgap(10);
        viewProfileLayout.setVgap(12);
        viewProfileLayout.add(records, 5, 2);
/*        viewProfileLayout.add(profileRecords, 4, 3);*/
        viewProfileLayout.add(profileSelector, 5, 15);
        viewProfileLayout.add(labelFirstNameInfo, 5, 3);
        viewProfileLayout.add(labelLastNameInfo, 5, 4);
        viewProfileLayout.add(labelFirstNameInfoRecords, 6, 3);
        viewProfileLayout.add(labelLastNameInfoRecords, 6, 4);
        viewProfileLayout.add(returnToHome, 5, 18);
        viewProfileLayout.setHalignment(profileSelector, HPos.CENTER);
        viewProfileLayout.setHalignment(records, HPos.CENTER);
        viewProfileLayout.setHalignment(labelFirstNameInfoRecords, HPos.CENTER);
        viewProfileLayout.setHalignment(labelLastNameInfoRecords, HPos.CENTER);
        viewProfileLayout.setHalignment(labelFirstNameInfo, HPos.CENTER);
        viewProfileLayout.setHalignment(labelLastNameInfo, HPos.CENTER);
        viewMyProfile = new Scene(viewProfileLayout, 255, 400);

        // Import Stylesheets
        viewTripDetails.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        chooseTripScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        viewMyProfile.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        primaryStage.getIcons().add(new Image("https://i.ibb.co/zxym2f8/train.png"));

        // Start Primarystage and define starting scene
        primaryStage.setTitle("OV App");
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setScene(chooseTripScene);

    }
}
