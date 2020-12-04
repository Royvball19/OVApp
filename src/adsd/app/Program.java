package adsd.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Program extends Application
{

    private static DataHandler datahandler;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
        Parent startScherm = loader.load();
        primaryStage.setTitle("TravelYroute OV App");
        primaryStage.setScene(new Scene(startScherm));
        primaryStage.show();
        primaryStage.setResizable(false);

        startScherm.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
    {
        // Datahandler import
        datahandler = new DataHandler();
        datahandler.readFromJSON();

        launch(args);

        Locale.setDefault(new Locale("nl", "NL"));
        ResourceBundle rb = ResourceBundle.getBundle("lang");


        // Datahandler writeToJSON always at the end of the application
        datahandler.writeToJSON();

    }
}
