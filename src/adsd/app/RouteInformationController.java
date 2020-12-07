package adsd.app;

import adsd.app.DataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

public class RouteInformationController extends HomeScreenController {

    ResourceBundle rb = ResourceBundle.getBundle("lang");

    @FXML ToolBar myToolBar;

    @FXML Label labelLocFrom;
    @FXML Label labelLocFromInfo;
    @FXML Label labelLocTo;
    @FXML Label labelLocToInfo;
    @FXML Label labelTravelTime;
    @FXML Label labelTravelTimeInfo;
    @FXML Label labelPrice;
    @FXML Label labelPriceInfo;
    @FXML Label labelDepartureTime;
    @FXML Label labelDepartureTimeInfo;
    @FXML Label labelArrivalTime;
    @FXML Label labelArrivalTimeInfo;

    DataHandler dataHandler;

    private int selectedTrip;


    public void initialize() throws FileNotFoundException {

        dataHandler = new DataHandler();
        dataHandler.readFromJSON();

        labelLocFrom.setText(rb.getString("locFrom"));
        labelLocTo.setText(rb.getString("locTo"));
        labelTravelTime.setText(rb.getString("travelTime"));
        labelPrice.setText(rb.getString("price"));
        labelDepartureTime.setText(rb.getString("RouteInfoDepartureTime"));
        labelArrivalTime.setText(rb.getString("RouteInfoArrivalTime"));

        labelLocFromInfo.setText(dataHandler.getTrip(0).getLocationFrom());
        labelLocToInfo.setText(dataHandler.getTrip(0).getLocationTo());
        labelTravelTimeInfo.setText(dataHandler.getTrip(0).getTravelTime());
        labelPriceInfo.setText(dataHandler.getTrip(0).getPrice());
        labelDepartureTimeInfo.setText(dataHandler.getTrip(0).getDepartureTime());
        labelArrivalTimeInfo.setText(dataHandler.getTrip(0).getArrivalTime());

//        labelLocFromInfo.setText(dataHandler.getTrip(HomeScreen.getSelectedTrip()).getLocationFrom());

        System.out.println("RouteInformation controller : " + getSelectedTrip());



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

    public void setSelectedTrip(int selectedTrip){
        this.selectedTrip = selectedTrip;
    }

    public int getSelectedTrip(){
        return selectedTrip;
    }
}
