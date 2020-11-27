package adsd.app;


import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Application;

import java.util.ResourceBundle;
import java.util.Scanner;

public class GUI extends Application
{
    private Scene chooseTripScene, viewTripDetails, viewMyProfile;
    private DataHandler dataHandler;
    private Scanner in        = new Scanner(System.in);
    private ResourceBundle rb = ResourceBundle.getBundle("lang");

    @Override
    public void start(Stage primaryStage) throws Exception {

        dataHandler = new DataHandler();
        dataHandler.readFromJSON();

        // GUI Labels
        Label departureRoute = new Label(rb.getString("departureRoute"));
        departureRoute.setId("departureRouteText");

        Label departureTime = new Label(rb.getString("departureTime"));
        departureTime.setId("departureTimeText");

        Label showTripInformation = new Label(rb.getString("showTripInformation"));
        showTripInformation.setStyle("-fx-font-weight: bold");

        Label records = new Label(rb.getString("records"));
        records.setStyle("-fx-font-weight: bold");

        Label profileRecords = new Label(rb.getString("profileRecords"));

        Label locationFrom = new Label();

        Label locationTo = new Label();

        Label tripTime = new Label();

        Label tripPrice = new Label();


        // Buttons and clickable items
        ToolBar toolBar = new ToolBar();
        toolBar.setId("toolbar");

        Button planTripButton = new Button(rb.getString("planTripButton"));
        planTripButton.setId("Testbutton");

        Button chooseOtherTrip = new Button(rb.getString("chooseOtherTrip"));

        Button addToFavorite = new Button(rb.getString("addToFavorite"));

        Button myProfileButton = new Button();
        toolBar.getItems().add(myProfileButton);
        myProfileButton.setId("testButtonImage");

        Button myFavoriteButton = new Button();
        toolBar.getItems().add(myFavoriteButton);
        myFavoriteButton.setId("testButtonImage2");

        Button chooseLangButton = new Button();
        toolBar.getItems().add(chooseLangButton);
        chooseLangButton.setId("testButtonImage3");


        // Choice boxes
        ChoiceBox choicebox = new ChoiceBox();
        choicebox.getItems().addAll(rb.getString("choicebox"), "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
        choicebox.getSelectionModel().select(0);

        ChoiceBox tripOption = new ChoiceBox();

        // Loop to add trips to tripOption choicebox
        for (int i = 0; i < dataHandler.getTripList().size(); i++ )
        {
            tripOption.getItems().add(dataHandler.getTrip(i).getLocationFrom() + " -> " + dataHandler.getTrip(i).getLocationTo());
        }

        // Listener for selecting the trip
        tripOption.getSelectionModel().selectedIndexProperty().addListener((v, oldValue, newValue) ->
        {
            locationFrom.setText(rb.getString("locationFrom") + dataHandler.getTrip((Integer) newValue).getLocationFrom());
            locationTo.setText(rb.getString("locationTo") + dataHandler.getTrip((Integer) newValue).getLocationTo());
            tripTime.setText(rb.getString("tripTime") + dataHandler.getTrip((Integer) newValue).getTravelTime());
            tripPrice.setText(rb.getString("tripPrice") + dataHandler.getTrip((Integer) newValue).getPrice());
        });


        // Button action listeners
        planTripButton.setOnAction(e ->
        {
            primaryStage.setScene(viewTripDetails);
        });

        chooseOtherTrip.setOnAction(e ->
        {
            primaryStage.setScene(chooseTripScene);
        });

        myProfileButton.setOnAction(e ->
        {
            primaryStage.setScene(viewMyProfile);
        });


        // Layout ChooseTripScene (FirstScene)
        GridPane chooseTripLayout = new GridPane();
        chooseTripLayout.setId("chooseTripLayout");
        chooseTripLayout.setHgap(10);
        chooseTripLayout.setVgap(12);
        chooseTripLayout.add(toolBar, 4, 0);
        chooseTripLayout.add(tripOption, 4, 5);
        chooseTripLayout.add(departureRoute, 4, 4);
        chooseTripLayout.add(departureTime, 4, 7);
        chooseTripLayout.add(choicebox, 4, 8);
        chooseTripLayout.add(planTripButton, 4, 12);
        chooseTripLayout.setHalignment(departureTime, HPos.CENTER);
        chooseTripLayout.setHalignment(departureRoute, HPos.CENTER);
        chooseTripLayout.setHalignment(tripOption, HPos.CENTER);
        chooseTripLayout.setHalignment(choicebox, HPos.CENTER);
        chooseTripLayout.setHalignment(planTripButton, HPos.CENTER);
        chooseTripScene = new Scene(chooseTripLayout, 265, 400);

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
        viewTripLayout.setHalignment(showTripInformation, HPos.CENTER);
        viewTripLayout.setHalignment(chooseOtherTrip, HPos.CENTER);
        viewTripLayout.setHalignment(addToFavorite, HPos.CENTER);
        viewTripDetails = new Scene(viewTripLayout, 265, 400);

        // Layout viewProfileLayout (ThirdScene)
        GridPane viewProfileLayout = new GridPane();
        viewProfileLayout.setId("viewProfileLayout");
        viewProfileLayout.setHgap(10);
        viewProfileLayout.setVgap(12);
        viewProfileLayout.add(records, 4, 2);
        viewProfileLayout.add(profileRecords, 4, 3);
        viewProfileLayout.setHalignment(records, HPos.CENTER);
        viewProfileLayout.setHalignment(profileRecords, HPos.LEFT);
        viewMyProfile = new Scene(viewProfileLayout, 265, 400);

        // Import Stylesheets
        viewTripDetails.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        chooseTripScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        viewMyProfile.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // Start Primarystage and define starting scene
        primaryStage.setTitle("OV App");
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setScene(chooseTripScene);

    }
}
