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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

public class HomeScreenController {

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


    public void initialize() throws FileNotFoundException {

        dataHandler = new DataHandler();
        dataHandler.readFromExternalData();

        departureRoute.setText((rb.getString("departureRoute")));
        departureRoute.setStyle("-fx-font-weight: bold");


        departureTime.setText((rb.getString("departureTime")));
        departureTime.setStyle("-fx-font-weight: bold");

        planTripButton.setText((rb.getString("planTripButton")));

        for (int i = 0; i < dataHandler.getTripList().size(); i++) {
            tripOption.getItems().add(dataHandler.getTrip(i).getLocationFrom() + " -> " + dataHandler.getTrip(i).getLocationTo());
        }

        timeOption.getItems().addAll(rb.getString("choicebox"), "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
        timeOption.getSelectionModel().select(0);
        dateOption.getItems().addAll(rb.getString("choiceboxdate"), "Vandaag", "Morgen", "Overmorgen");
        dateOption.getSelectionModel().select(0);


    }




    public void showTrip(ActionEvent event) throws IOException {


        if (tripOption.getSelectionModel().getSelectedIndex() == -1) {
            // set text to a box with message: "please select"
        } else {

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

    public void showMyProfileButton(ActionEvent event) throws IOException {
        Parent homeScreenParent2 = FXMLLoader.load(getClass().getResource("MyProfile.fxml"));
        Scene myProfileScene = new Scene(homeScreenParent2);

        myProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // This line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(myProfileScene);
        window.show();
    }

    public void showMyFavoriteTrips(ActionEvent event) throws IOException {
        Parent homeScreenParent3 = FXMLLoader.load(getClass().getResource("MyFavoriteTrips.fxml"));
        Scene myFavoriteTrips = new Scene(homeScreenParent3);

        myFavoriteTrips.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // This line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(myFavoriteTrips);
        window.show();
    }


}