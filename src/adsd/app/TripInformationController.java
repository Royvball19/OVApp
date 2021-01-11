package adsd.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.Scanner;

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
    @FXML private Button addFavoriteTripButton;
    int userID;


    DataHandler dataHandler;
    MyProfileController myProfileController;





    public void initialize() throws FileNotFoundException
    {

        dataHandler = new DataHandler();
        dataHandler.readFromExternalData();

        WebEngine webEngine = mapWebView.getEngine();
        String url = "file:///C:/Users/royva/IdeaProjects/OVApp/src/adsd/app/index.html";
        webEngine.load(url);

/*        WebEngine webEngine = mapWebView.getEngine();
        URL url = this.getClass().getResource("/adsd/app/index.html");
        webEngine.load(url.toString());*/

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
        Parent HomeScreenParent = FXMLLoader.load(getClass().getResource("fxml/HomeScreen.fxml"));
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
        Parent homeScreenParent = FXMLLoader.load(getClass().getResource("fxml/MyFavoriteTrips.fxml"));
        Scene myFavoriteTrips = new Scene(homeScreenParent);

        myFavoriteTrips.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // This line gets the stage information
        Stage window = (Stage) myToolBar.getScene().getWindow();

        window.setScene(myFavoriteTrips);
        window.show();
    }

    public void showMyProfileButton (ActionEvent event) throws IOException
    {
        Parent homeScreenParent2 = FXMLLoader.load(getClass().getResource("fxml/MyProfile.fxml"));
        Scene myProfileScene = new Scene(homeScreenParent2);

        myProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // This line gets the stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(myProfileScene);
        window.show();
    }

    public void addFavoriteTripButton (ActionEvent event) throws IOException
    {
        myProfileController = new MyProfileController();
        try {
            File myObj = new File("currentuser.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                dataHandler.getProfile(Integer.parseInt(data)).addTrip(0, labelLocFromInfo.getText(), labelLocToInfo.getText(), "0", 0, 0, 0, 0);
                dataHandler.writeToExternalData();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void setLocFrom(String test)
    {
        labelLocFromInfo.setText(test);
    }

    public void sendTime(String time){


    }

}
