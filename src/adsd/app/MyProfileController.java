package adsd.app;

import adsd.app.DataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

public class MyProfileController {

    ResourceBundle rb = ResourceBundle.getBundle("lang");

    DataHandler dataHandler;

    @FXML Label labelFirstName;
    @FXML Label labelLastName;
    @FXML Label firstName;
    @FXML Label lastName;
    @FXML Button showHomeScreen;
    @FXML ToolBar myToolBar;

    public void initialize() throws FileNotFoundException {

        dataHandler = new DataHandler();
        dataHandler.readFromJSON();

        labelFirstName.setText(rb.getString("labelFirstName"));
        labelLastName.setText(rb.getString("labelLastName"));



//        firstName.setText(dataHandler.getProfile(i).getFirstName());
//        lastName.setText(dataHandler.getProfile(i).getLastName());

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
}
