package adsd.app;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.skin.ChoiceBoxSkin;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ResourceBundle;

public class HomeScreenController  {

    ResourceBundle rb = ResourceBundle.getBundle("lang");

    @FXML private Label departureRoute;
    @FXML private Label departureTime;

    @FXML private ChoiceBox tripOption;
    @FXML private ChoiceBox timeOption;
    @FXML private ChoiceBox dateOption;
    @FXML private Button planTripButton;

    DataHandler dataHandler;



    public void initialize() throws FileNotFoundException {

        dataHandler = new DataHandler();
        dataHandler.readFromJSON();

        departureRoute.setText((rb.getString("departureRoute")));
        departureRoute.setStyle("-fx-font-weight: bold");


        departureTime.setText((rb.getString("departureTime")));
        departureTime.setStyle("-fx-font-weight: bold");

        planTripButton.setText((rb.getString("planTripButton")));

        for (int i = 0; i < dataHandler.getTripList().size(); i++ )
        {
            tripOption.getItems().add(dataHandler.getTrip(i).getLocationFrom() + " -> " + dataHandler.getTrip(i).getLocationTo());
        }

        timeOption.getItems().addAll(rb.getString("choicebox"), "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
        timeOption.getSelectionModel().select(0);
        dateOption.getItems().addAll(rb.getString("choiceboxdate"), "Vandaag", "Morgen", "Overmorgen");
        dateOption.getSelectionModel().select(0);
    }
}
