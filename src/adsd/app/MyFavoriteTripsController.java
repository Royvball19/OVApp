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

public class MyFavoriteTripsController
{

    ResourceBundle rb = ResourceBundle.getBundle("lang");

    DataHandler dataHandler;


    @FXML ToolBar myToolBar;

    public void initialize() throws FileNotFoundException
    {



    }

    public void showHomeScreen(ActionEvent event) throws IOException
    {
        Parent tripInfoParent = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
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
        Parent tripInfoParent = FXMLLoader.load(getClass().getResource("MyProfile.fxml"));
        Scene routeInfoScene = new Scene(tripInfoParent);

        // Gets stage information
        Stage window = (Stage) myToolBar.getScene().getWindow();

        routeInfoScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        // Set scene to go back
        window.setScene(routeInfoScene);
        window.show();
    }
}
