package adsd.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MyFavoriteTripsController
{

    ResourceBundle rb = ResourceBundle.getBundle("lang");

    DataHandler dataHandler;
    @FXML TableView<Trip> favTrips;
    @FXML TableColumn<Trip, String> favTripsFromCollum;
    @FXML TableColumn<Trip, String> favTripsToCollum;
    @FXML ToolBar myToolBar;

    int currentUser;


    public void initialize() throws FileNotFoundException
    {
        dataHandler = new DataHandler();
        dataHandler.readFromExternalData();

        favTripsFromCollum.setCellValueFactory( new PropertyValueFactory<>("locationFrom"));
        favTripsToCollum.setCellValueFactory( new PropertyValueFactory<>("locationTo"));

        try {
            File myObj = new File("currentuser.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                for (int i = 0; i < dataHandler.getProfile(Integer.parseInt(data)).myTripList().size(); i++)
                {
                    favTrips.getItems().add(dataHandler.getProfile(Integer.parseInt(data)).myTripList().get(i));
                }
                dataHandler.writeToExternalData();

                currentUser = Integer.parseInt(data);
            }
            myReader.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void showHomeScreen(ActionEvent event) throws IOException
    {
        Parent tripInfoParent = FXMLLoader.load(getClass().getResource("fxml/HomeScreen.fxml"));
        Scene routeInfoScene = new Scene(tripInfoParent);

        // Gets stage information
        Stage window = (Stage) myToolBar.getScene().getWindow();

        routeInfoScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        // Set scene to go back
        window.setScene(routeInfoScene);
        window.show();
    }

    public void showMyProfile(ActionEvent event) throws IOException
    {
        Parent tripInfoParent = FXMLLoader.load(getClass().getResource("fxml/MyProfile.fxml"));
        Scene routeInfoScene = new Scene(tripInfoParent);

        // Gets stage information
        Stage window = (Stage) myToolBar.getScene().getWindow();

        routeInfoScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        // Set scene to go back
        window.setScene(routeInfoScene);
        window.show();
    }

    public void removeFavTripButton(ActionEvent event) throws IOException
    {
        dataHandler.getProfile(currentUser).removeTrip(favTrips.getSelectionModel().getSelectedIndex());
        favTrips.getItems().remove(favTrips.getSelectionModel().getSelectedIndex());
        dataHandler.writeToExternalData();
    }
}
