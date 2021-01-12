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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
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

    private String lang;
    private String country;



    public void initialize() throws IOException {

        lang = Files.readAllLines(Paths.get("currentLang.txt")).get(0);
        country = Files.readAllLines(Paths.get("currentLang.txt")).get(1);
        Locale.setDefault(new Locale(lang, country));

        ResourceBundle rb = ResourceBundle.getBundle("lang");

        dataHandler = new DataHandler();
        dataHandler.readFromExternalData();

/*        Roy old line
        WebEngine webEngine = mapWebView.getEngine();
        String url = "file:///C:/Users/royva/IdeaProjects/OVApp/src/adsd/app/index.html";
        webEngine.load(url);*/

/*        Yannick old line
        WebEngine webEngine = mapWebView.getEngine();
        String url = "file://Users/yannickvdbos/Downloads/OVApp/src/adsd/app/index.html";
        webEngine.load(url);*/

        String path = "src/adsd/app/index.html";
        WebEngine webEngine = mapWebView.getEngine();
        URL url = new File(path).toURI().toURL();
        webEngine.load(url.toString());


/*
        Old line where it doesnt update the map
        WebEngine webEngine = mapWebView.getEngine();
        URL url = this.getClass().getResource("index.html");
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

    public void changeLangEng(ActionEvent event) throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get("currentLang.txt"));
        lines.set(0, "en");
        lines.set(1, "US");
        Files.write(Paths.get("currentLang.txt"), lines); // You can add a charset and other options too


        initialize();

    }

    public void changeLangNed(ActionEvent event) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get("currentLang.txt"));
        lines.set(0, "nl");
        lines.set(1, "NL");
        Files.write(Paths.get("currentLang.txt"), lines); // You can add a charset and other options too

        initialize();

    }
}
