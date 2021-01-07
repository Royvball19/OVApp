package adsd.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class TripInformationController extends HomeScreenController
{

    ResourceBundle rb = ResourceBundle.getBundle("lang");

    @FXML ToolBar myToolBar;
    @FXML WebView mapWebView;

    @FXML private Label labelLocFrom;
    @FXML private Label labelLocFromInfo;
    @FXML private Label labelLocTo;
    @FXML private Label labelLocToInfo;
    @FXML private Label labelTravelTime;
    @FXML private Label labelTravelTimeInfo;
    @FXML private Label labelDepartureTime;
    @FXML private Label labelDepartureTimeInfo;
    @FXML private Label labelArrivalTime;
    @FXML private Label labelArrivalTimeInfo;
    @FXML private Label labelTitle;

    DataHandler dataHandler;





    public void initialize() throws FileNotFoundException
    {

        dataHandler = new DataHandler();
        dataHandler.readFromExternalData();

        WebEngine webEngine = mapWebView.getEngine();
        String url = "index.html";
        webEngine.load(url);

        labelLocFrom.setText(rb.getString("locFrom"));
        labelLocTo.setText(rb.getString("locTo"));
        labelTravelTime.setText(rb.getString("travelTime"));

        labelDepartureTime.setText(rb.getString("RouteInfoDepartureTime"));
        labelArrivalTime.setText(rb.getString("RouteInfoArrivalTime"));







    }

    public void sendInput(int posA, int posB)
    {
        DecimalFormat df = new DecimalFormat("00.00");

        labelLocFromInfo.setText(dataHandler.getTrip(posA).getLocationFrom());
        labelLocToInfo.setText(dataHandler.getTrip(posA).getLocationTo());
        labelTravelTimeInfo.setText(dataHandler.getTrip(posA).getTripTimeObj(posB).getTravelTime().toString() + " "+ rb.getString("minutes"));
        labelDepartureTimeInfo.setText(toDubbleDot(String.valueOf(df.format(dataHandler.getTrip(posA).getTripTimeObj(posB).getDepTime()))));
        labelArrivalTimeInfo.setText(toDubbleDot(String.valueOf(df.format(dataHandler.getTrip(posA).getTripTimeObj(posB).getArivTime()))));
    }

    public void showHomeScreen(ActionEvent event) throws IOException
    {
        Parent HomeScreenParent = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
        Scene MyProfileScene = new Scene(HomeScreenParent);

        // Gets stage information

        Stage window = (Stage) myToolBar.getScene().getWindow();

        MyProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // Set scene to go back
        window.setScene(MyProfileScene);
        window.show();
    }


    public void showMyFavoriteTrips (ActionEvent event) throws IOException
    {
        Parent homeScreenParent = FXMLLoader.load(getClass().getResource("MyFavoriteTrips.fxml"));
        Scene myFavoriteTrips = new Scene(homeScreenParent);

        myFavoriteTrips.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // This line gets the stage information
        Stage window = (Stage) myToolBar.getScene().getWindow();

        window.setScene(myFavoriteTrips);
        window.show();
    }

    public void showMyProfileButton (ActionEvent event) throws IOException
    {
        Parent homeScreenParent2 = FXMLLoader.load(getClass().getResource("MyProfile.fxml"));
        Scene myProfileScene = new Scene(homeScreenParent2);

        myProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // This line gets the stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(myProfileScene);
        window.show();
    }

    public void setLocFrom(String test)
    {
        labelLocFromInfo.setText(test);
    }

    public void sendTime(String time){


    }

}
