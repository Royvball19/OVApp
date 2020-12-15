package adsd.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

public class TripInformationController extends HomeScreenController {

    ResourceBundle rb = ResourceBundle.getBundle("lang");

    @FXML ToolBar myToolBar;

    @FXML private Label labelLocFrom;
    @FXML private Label labelLocFromInfo;
    @FXML private Label labelLocTo;
    @FXML private Label labelLocToInfo;
    @FXML private Label labelTravelTime;
    @FXML private Label labelTravelTimeInfo;
    @FXML private Label labelPrice;
    @FXML private Label labelPriceInfo;
    @FXML private Label labelDepartureTime;
    @FXML private Label labelDepartureTimeInfo;
    @FXML private Label labelArrivalTime;
    @FXML private Label labelArrivalTimeInfo;
    @FXML private Label labelTitle;

    DataHandler dataHandler;





    public void initialize() throws FileNotFoundException {

        dataHandler = new DataHandler();
        dataHandler.readFromExternalData();



        labelLocFrom.setText(rb.getString("locFrom"));
        labelLocTo.setText(rb.getString("locTo"));
        labelTravelTime.setText(rb.getString("travelTime"));
        labelPrice.setText(rb.getString("price"));
        labelDepartureTime.setText(rb.getString("RouteInfoDepartureTime"));
        labelArrivalTime.setText(rb.getString("RouteInfoArrivalTime"));





    }

    public void sendInput(int selectedTrip){

        labelLocFromInfo.setText(dataHandler.getTrip(selectedTrip).getLocationFrom());
        labelLocToInfo.setText(dataHandler.getTrip(selectedTrip).getLocationTo());
        labelTravelTimeInfo.setText(dataHandler.getTrip(selectedTrip).getTravelTime());
        labelPriceInfo.setText(dataHandler.getTrip(selectedTrip).getPrice());




        System.out.println("RouteInformation controller : " + selectedTrip);

    }

    public void showHomeScreen(ActionEvent event) throws IOException {
        Parent HomeScreenParent = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
        Scene MyProfileScene = new Scene(HomeScreenParent);

        // Gets stage information

        Stage window = (Stage) myToolBar.getScene().getWindow();

        MyProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // Set scene to go back
        window.setScene(MyProfileScene);
        window.show();
    }


    public void showMyFavoriteTrips (ActionEvent event) throws IOException {
        Parent homeScreenParent = FXMLLoader.load(getClass().getResource("MyFavoriteTrips.fxml"));
        Scene myFavoriteTrips = new Scene(homeScreenParent);

        myFavoriteTrips.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // This line gets the stage information
        Stage window = (Stage) myToolBar.getScene().getWindow();

        window.setScene(myFavoriteTrips);
        window.show();
    }

    public void showMyProfileButton (ActionEvent event) throws IOException {
        Parent homeScreenParent2 = FXMLLoader.load(getClass().getResource("MyProfile.fxml"));
        Scene myProfileScene = new Scene(homeScreenParent2);

        myProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // This line gets the stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(myProfileScene);
        window.show();
    }

    public void setLocFrom(String test) {
        labelLocFromInfo.setText(test);
    }



}
