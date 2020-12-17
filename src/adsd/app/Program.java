package adsd.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

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
        System.out.println("Loading driver...");
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!\n");
        } catch (ClassNotFoundException e)
        {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }

        // Datahandler import
        datahandler = new DataHandler();
        datahandler.readFromExternalData();

        // Application.launch(GUI.class, args);
        launch(args);

        Locale.setDefault(new Locale("nl", "NL"));
        ResourceBundle rb = ResourceBundle.getBundle("lang");

        // Datahandler writeToJSON always at the end of the application
        datahandler.writeToExternalData();

    }
}
